
package comp;

import ast.*;
import lexer.*;
import java.io.*;
import java.util.*;

public class Compiler {

	private SymbolTable		symbolTable;
	private Lexer			lexer;
	private ErrorSignaller	signalError;
	private Method currentMethod;
	private KraClass currentClass;
	private boolean hasReturn;
	private int breakPossible;
	// compile must receive an input with an character less than
	// p_input.lenght
	public Program compile(char[] input, PrintWriter outError) {

		ArrayList<CompilationError> compilationErrorList = new ArrayList<>();
		signalError = new ErrorSignaller(outError, compilationErrorList);
		symbolTable = new SymbolTable();
		lexer = new Lexer(input, signalError);
		signalError.setLexer(lexer);
		hasReturn = false;
		breakPossible = 0;

		Program program = null;
		lexer.nextToken();
		program = program(compilationErrorList);
		return program;
	}

	private Program program(ArrayList<CompilationError> compilationErrorList) {
		// Program ::= KraClass { KraClass }
		ArrayList<MetaobjectCall> metaobjectCallList = new ArrayList<>();
		ArrayList<KraClass> kraClassList = new ArrayList<>();
		try {
			while ( lexer.token == Symbol.MOCall ) {
				metaobjectCallList.add(metaobjectCall());
			}
			kraClassList.add(classDec());
			while ( lexer.token == Symbol.CLASS )
				kraClassList.add(classDec());
			if ( lexer.token != Symbol.EOF ) {
				signalError.showError("End of file expected");
			}
			
			boolean hasProgram = false;
			for (KraClass k : kraClassList) {
				
				if(k.getName().equals("Program")){
					hasProgram = true;
					break;
				}
			}
			if(!hasProgram)
				signalError.showError("Source code without a class 'Program'");;
		}
		catch( RuntimeException e) {
			
		}
		return new Program(kraClassList, metaobjectCallList, compilationErrorList);
	}

	@SuppressWarnings("incomplete-switch")
	private MetaobjectCall metaobjectCall() {
		String name = lexer.getMetaobjectName();
		lexer.nextToken();
		ArrayList<Object> metaobjectParamList = new ArrayList<>();
		if ( lexer.token == Symbol.LEFTPAR ) {
			// metaobject call with parameters
			lexer.nextToken();
			while ( lexer.token == Symbol.LITERALINT || lexer.token == Symbol.LITERALSTRING ||
					lexer.token == Symbol.IDENT ) {
				switch ( lexer.token ) {
				case LITERALINT:
					metaobjectParamList.add(lexer.getNumberValue());
					break;
				case LITERALSTRING:
					metaobjectParamList.add(lexer.getLiteralStringValue());
					break;
				case IDENT:
					metaobjectParamList.add(lexer.getStringValue());
				}
				lexer.nextToken();
				if ( lexer.token == Symbol.COMMA ) 
					lexer.nextToken();
				else
					break;
			}
			if ( lexer.token != Symbol.RIGHTPAR ) 
				signalError.showError("')' expected after metaobject call with parameters");
			else
				lexer.nextToken();
		}
		if ( name.equals("nce") ) {
			if ( metaobjectParamList.size() != 0 )
				signalError.showError("Metaobject 'nce' does not take parameters");
		}
		else if ( name.equals("ce") ) {
			if ( metaobjectParamList.size() != 3 && metaobjectParamList.size() != 4 )
				signalError.showError("Metaobject 'ce' take three or four parameters");
			if ( !( metaobjectParamList.get(0) instanceof Integer)  )
				signalError.showError("The first parameter of metaobject 'ce' should be an integer number");
			if ( !( metaobjectParamList.get(1) instanceof String) ||  !( metaobjectParamList.get(2) instanceof String) )
				signalError.showError("The second and third parameters of metaobject 'ce' should be literal strings");
			if ( metaobjectParamList.size() >= 4 && !( metaobjectParamList.get(3) instanceof String) )  
				signalError.showError("The fourth parameter of metaobject 'ce' should be a literal string");
			
		}
			
		return new MetaobjectCall(name, metaobjectParamList);
	}

	private KraClass classDec() {
		if ( lexer.token != Symbol.CLASS ) 
			signalError.showError("'class' expected");
		lexer.nextToken();
		
		if ( lexer.token != Symbol.IDENT )
			signalError.show(ErrorSignaller.ident_expected);
		
		String className = lexer.getStringValue();
		if(symbolTable.getInGlobal(className) != null) 
			signalError.showError("Duplicated class id " + className);
		currentClass = new KraClass(className);
		symbolTable.putInGlobal(className, currentClass);
		lexer.nextToken();
		
		if ( lexer.token == Symbol.EXTENDS ) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show(ErrorSignaller.ident_expected);
			String superclassName = lexer.getStringValue();
			KraClass superClass = (KraClass) symbolTable.getInGlobal(superclassName);
			
			if(superClass == null) 
				signalError.showError("No class " + superclassName + "declared.");
			
			if(className.equals(superclassName))
				signalError.showError("A class can't extends itself");
			
			currentClass.setSuper(superClass);
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.LEFTCURBRACKET )
			signalError.showError("{ expected", true);
		lexer.nextToken();

		while (lexer.token == Symbol.PRIVATE || lexer.token == Symbol.PUBLIC) {
			Symbol qualifier;
			switch (lexer.token) {
			case PRIVATE:
				lexer.nextToken();
				qualifier = Symbol.PRIVATE;
				break;
			case PUBLIC:
				lexer.nextToken();
				qualifier = Symbol.PUBLIC;
				break;
			default:
				signalError.showError("private, or public expected");
				qualifier = Symbol.PUBLIC;
			}
			
			Type t = type();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");
			String name = lexer.getStringValue();
			lexer.nextToken();
			
			if ( lexer.token == Symbol.LEFTPAR ) {
				
				methodDec(t, name, qualifier);
			}
			else if ( qualifier != Symbol.PRIVATE )
				signalError.showError("Attempt to declare a public instance variable");
			else if (t == Type.voidType)
				signalError.showError("Attempt to declare a void instance variable");
			else
				instanceVarDec(t, name);
		}
		
		if(currentClass.getName().equals("Program") && currentClass.searchPublicMethod("run") == null)
			signalError.showError("The class Program must have a public method run");
		
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			signalError.showError("public/private or \"}\" expected");
		lexer.nextToken();
		
		symbolTable.removeLocalIdent();
		return currentClass;
	}

	private void instanceVarDec(Type type, String name) {
		if(currentClass.getVar(name) != null)
			signalError.showError("Variable " + name + "is already declared in this class.");
		if(currentClass.searchPrivateMethod(name) != null || currentClass.searchPublicMethod(name) != null)
			signalError.showError(name + " is already declared as a method.");
		
		InstanceVariable v = new InstanceVariable(name, type);
		currentClass.addVariable(v);
		
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");
			String variableName = lexer.getStringValue();
			lexer.nextToken();
			
			if(currentClass.getVar(name) != null)
				signalError.showError("Variable " + variableName + "is already declared in this class");
			if(currentClass.searchMethod(variableName) != null)
				signalError.showError(variableName + " is already declared as a method.");
			
			v =  new InstanceVariable(variableName, type);
			currentClass.addVariable(v);	
		}
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();
	}

	private void methodDec(Type type, String name, Symbol qualifier) {
		if(currentClass.searchMethod(name) != null)
			signalError.showError("Method " + name + " is already defined in this class");
		if(currentClass.getVar(name) != null)
			signalError.showError("Identifier " + name + "is declared as a Variable in this scope");
		currentMethod = new Method(type, name, qualifier);
		currentClass.addMethod(currentMethod, qualifier);
		
		lexer.nextToken();
		if ( lexer.token != Symbol.RIGHTPAR ) 
			formalParamDec();
		if ( lexer.token != Symbol.RIGHTPAR ) 
			signalError.showError(") expected");
		
	
		if(currentClass.getName().equals("Program") && name.equals("run")){
			if(currentMethod.getParamList().getSize() > 0)
				signalError.showError("The run method must be parameterless");
			if(type != Type.voidType)
				signalError.showError("The run method must have void return type");
			if(qualifier != Symbol.PUBLIC)
				signalError.showError("The run method must be public");
		}
		
		
		KraClass superClass = currentClass.getSuperclass();
		Method superMethod = null;
		
		while(superClass != null){
			if((superMethod = (superClass.searchPublicMethod(name)))== null)
				superClass = superClass.getSuperclass();
			else
				break;
		}
		if(superMethod != null){
			if(superMethod.getReturnType() != type)
				signalError.showError("Invalid override: missmatch return type");
			
			ParamList superParamList = superMethod.getParamList(); 
			ParamList methParamList = currentMethod.getParamList();
			
			if(superParamList.getSize() != methParamList.getSize())
				signalError.showError("Invalid override: missmatch number of parameters");
			
			Iterator<Parameter> superIt = superParamList.elements();
			Iterator<Parameter> paramIt = methParamList.elements();
			while(superIt.hasNext()){
				if(!(superIt.next().getType().isCompatible(paramIt.next().getType())))
					signalError.showError("Invalid override: missmatch type of parameters");
			}
		}
		
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTCURBRACKET ) 
			signalError.showError("{ expected");
		lexer.nextToken();
		
		hasReturn = false;
		currentMethod.addStmtList(statementList());
		
		if(!hasReturn && type != Type.voidType)
			signalError.showError("Missing return statement in method + " + currentMethod.getName());
		
		if ( lexer.token != Symbol.RIGHTCURBRACKET ) 
			signalError.showError("} expected");
		lexer.nextToken();
		
		symbolTable.removeLocalIdent();
	}

	private void localDec() {
		Type type = type();
		if ( lexer.token != Symbol.IDENT ) 
			signalError.showError("Identifier expected");
		String name = lexer.getStringValue();
		Variable v = new Variable(name, type);
		
		if(symbolTable.getInLocal(name) != null)
			signalError.showError("double declaration of local variable " + name);
		symbolTable.putInLocal(name, v);
		currentMethod.addVar(v);
		lexer.nextToken();
		
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");
			
			name = lexer.getStringValue();
			v = new Variable(name, type);
			
			if(symbolTable.getInLocal(name) != null)
				signalError.showError("double declaration of local variable " + name);
			symbolTable.putInLocal(name, v);
			currentMethod.addVar(v);
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();		
	}

	private void formalParamDec() {
		// FormalParamDec ::= ParamDec { "," ParamDec }

		paramDec();
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			paramDec();
		}
	}

	private void paramDec() {
		// ParamDec ::= Type Id
		Type t = type();
		if ( lexer.token != Symbol.IDENT ) 
			signalError.showError("Identifier expected");
		
		String name = lexer.getStringValue();
		if(symbolTable.getInLocal(name) != null) 
			signalError.showError("Double declaration of parameter " + name);
		lexer.nextToken();
		
		Parameter p = new Parameter(name, t);
		symbolTable.putInLocal(name, p);
		currentMethod.addParameter(p) ;
	}

	private Type type() {
		// Type ::= BasicType | Id
		Type result;

		switch (lexer.token) {
		case VOID:
			result = Type.voidType;
			break;
		case INT:
			result = Type.intType;
			break;
		case BOOLEAN:
			result = Type.booleanType;
			break;
		case STRING:
			result = Type.stringType;
			break;
		case IDENT:
			String name = lexer.getStringValue();
			KraClass c = null;
			if((c = (KraClass)symbolTable.getInGlobal(name)) == null)
				signalError.showError(name + "is not declared as a class.");
			result = c;
			break;
		default:
			signalError.showError("Type expected");
			result = Type.undefinedType;
		}
		lexer.nextToken();
		return result;
	}

	private CompositeStatement compositeStatement() {
		lexer.nextToken();
		StatementList stmtList = statementList();
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			signalError.showError("} expected");
		lexer.nextToken();
		
		return new CompositeStatement(stmtList);
	}

	private StatementList statementList() {
		// CompStatement ::= "{" { Statement } "}"
		Symbol tk;
		StatementList stmtList = new StatementList();
		// statements always begin with an identifier, if, read, write, ...
		while ((tk = lexer.token) != Symbol.RIGHTCURBRACKET && tk != Symbol.ELSE)
			stmtList.add(statement());
		
		return stmtList;
	}

	private Statement statement() {
		switch (lexer.token) {
		case THIS:
		case IDENT:
		case SUPER:
		case INT:
		case BOOLEAN:
		case STRING:
			return assignExprLocalDec();
		case ASSERT:
			return assertStatement();
		case RETURN:
			return returnStatement();
		case READ:
			return readStatement();
		case WRITE:
			return writeStatement();
		case WRITELN:
			return writelnStatement();
		case IF:
			return ifStatement();
		case BREAK:
			return breakStatement();
		case DO:
			return doWhileStatement();
		case WHILE:
			return whileStatement();
		case SEMICOLON:
			return nullStatement();
		case LEFTCURBRACKET:
			return compositeStatement();
		default:
			signalError.showError("Statement expected");
			return null;
		}
	}

	private Statement assertStatement() {
		lexer.nextToken();
		int lineNumber = lexer.getLineNumber();
		Expr e = expr();
		if ( e.getType() != Type.booleanType )
			signalError.showError("boolean expression expected");
		if ( lexer.token != Symbol.COMMA ) {
			this.signalError.showError("',' expected after the expression of the 'assert' statement");
		}
		lexer.nextToken();
		if ( lexer.token != Symbol.LITERALSTRING ) {
			this.signalError.showError("A literal string expected after the ',' of the 'assert' statement");
		}
		String message = lexer.getLiteralStringValue();
		lexer.nextToken();
		if ( lexer.token == Symbol.SEMICOLON )
			lexer.nextToken();
		
		return new StatementAssert(e, lineNumber, message);
	}

	private boolean isType(String name) {
		return this.symbolTable.getInGlobal(name) != null;
	}

	private AssignStatement assignExprLocalDec() {

		if ( lexer.token == Symbol.INT || lexer.token == Symbol.BOOLEAN
				|| lexer.token == Symbol.STRING ||((lexer.token == Symbol.IDENT && isType(lexer.getStringValue()) && symbolTable.getInLocal(lexer.getStringValue()) == null)) ) {
			localDec();
		}
		else {
			Expr left = expr();
			if ( lexer.token == Symbol.ASSIGN ) {
				lexer.nextToken();
				Expr right = expr();
				
				if(!left.getType().isCompatible(right.getType()))
					this.signalError.showError("Type error in assignment");
				
				if ( lexer.token != Symbol.SEMICOLON )
					signalError.showError("';' expected", true);
				else
					lexer.nextToken();
				
				return new AssignStatement(left, right);
			}
			if(left instanceof MessageSendToVariable && left.getType() != Type.voidType)
				signalError.showError("Illegal call of non void method, this  be in right side of an assignment");
			
			return new AssignStatement(left, null);
				
		}
		return null;
	}

	private ExprList realParameters() {
		ExprList anExprList = new ExprList();

		if ( lexer.token != Symbol.LEFTPAR ) 
			signalError.showError("( expected");
		lexer.nextToken();
		if ( startExpr(lexer.token) ) 
			anExprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) 
			signalError.showError(") expected");
		lexer.nextToken();
		return anExprList;
	}

	private WhileStatement whileStatement() {
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) 
			signalError.showError("( expected");
		lexer.nextToken();
		
		Expr e = expr();
		if(e.getType() != Type.booleanType)
			signalError.showError("Boolean expression expected");
		
		if ( lexer.token != Symbol.RIGHTPAR ) 
			signalError.showError(") expected");
		lexer.nextToken();
		
		breakPossible++;
		Statement s = statement();
		breakPossible--;
		
		return new WhileStatement(e, s);
	}
	
	private DoWhileStatement doWhileStatement(){
		lexer.nextToken();
		
		if ( lexer.token != Symbol.LEFTCURBRACKET ) 
			signalError.showError("{ expected");
		
		breakPossible++;
		Statement s = statement();
		breakPossible--;
		
		if(lexer.token != Symbol.WHILE)
			signalError.showError("while expected");
		
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) 
			signalError.showError("( expected");
		lexer.nextToken();
		
		Expr e = expr();
		if(e.getType() != Type.booleanType)
			signalError.showError("Boolean expression expected");
		
		if ( lexer.token != Symbol.RIGHTPAR ) 
			signalError.showError(") expected");
		lexer.nextToken();
		
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.showError("';' expected", true);
		else
			lexer.nextToken();
		
		return new DoWhileStatement(s, e);
	}

	private IfStatement ifStatement() {
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) 
			signalError.showError("( expected");
		lexer.nextToken();
		
		Expr e = expr();
		
		if(e.getType() != Type.booleanType)
			signalError.showError("Boolean expression expected");
		if ( lexer.token != Symbol.RIGHTPAR ) 
			signalError.showError(") expected");
		lexer.nextToken();
		Statement ifs = statement();
		Statement elses = null;
		if ( lexer.token == Symbol.ELSE ) {
			lexer.nextToken();
			elses = statement();
		}
		
		return new IfStatement(e, ifs, elses);
	}

	private ReturnStatement returnStatement() {

		lexer.nextToken();
		Expr e = expr();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();
		
		if(this.currentMethod.getReturnType() == Type.voidType) 
			this.signalError.showError("Return value in a void method");
		if(!this.currentMethod.getReturnType().isCompatible(e.getType()))
			this.signalError.showError("Incompatible return value");
		
		hasReturn = true;
		return new ReturnStatement(e);
	}

	private ReadStatement readStatement() {
		ArrayList<Variable> readStmt = new ArrayList<>();
		boolean message = false;
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) 
			signalError.showError("( expected");
		lexer.nextToken();
		
		while (true) {
			if ( lexer.token == Symbol.THIS ) {
				message = true;
				lexer.nextToken();
				if ( lexer.token != Symbol.DOT ) 
					signalError.showError(". expected");
				lexer.nextToken();
			}
			
			if ( lexer.token != Symbol.IDENT )
				signalError.show(ErrorSignaller.ident_expected);

			if(lexer.token == Symbol.DOT)
				signalError.showError("Messages to variables aren't allowed in a read statement.");
			String name = lexer.getStringValue();
			lexer.nextToken();
			
			Variable v = null;
			
			if(message){
				if((v = currentClass.getVar(name)) == null)
					signalError.showError("Instance variable " + name + " isn't declared in this class.");
			}else{
				if((v = (Variable)symbolTable.getInLocal(name)) == null)
					signalError.showError("Variable" + name + "isn't declared in this scope.");
			}
			if(v.getType() != Type.stringType && v.getType() != Type.intType)
				signalError.showError("Only string and int types can be read.");
			
			readStmt.add(v);
			
			if ( lexer.token == Symbol.COMMA )
				lexer.nextToken();
			else
				break;
		}

		if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();
		
		return new ReadStatement(readStmt);
	}

	private WriteStatement writeStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) 
			signalError.showError("( expected");
		lexer.nextToken();
		ExprList exprList = exprList();
		
		Iterator<Expr> i = exprList.getElements();
		while(i.hasNext()){
			Expr e = i.next();
			
			if(e.getType() != Type.stringType && e.getType() != Type.intType)
				signalError.showError("Only string and int types can be writed.");			
		}
		
		if ( lexer.token != Symbol.RIGHTPAR ) 
			signalError.showError(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();
		
		return new WriteStatement(exprList);
	}

	private WritelnStatement writelnStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) 
			signalError.showError("( expected");
		lexer.nextToken();

		ExprList exprList = exprList();
		
		Iterator<Expr> i = exprList.getElements();
		while(i.hasNext()){
			Expr e = i.next();
			
			if(e.getType() != Type.stringType && e.getType() != Type.intType)
				signalError.showError("Only string and int types can be writed.");			
		}
		
		if ( lexer.token != Symbol.RIGHTPAR ) 
			signalError.showError(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();
		
		return new WritelnStatement(exprList);
	}

	private BreakStatement breakStatement() {
		if(breakPossible == 0)
			signalError.showError("Break statement out of a loop");
		
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();
		
		return new BreakStatement();
	}

	private NullStatement nullStatement() {
		lexer.nextToken();
		return new NullStatement();
	}

	private ExprList exprList() {
		// ExpressionList ::= Expression { "," Expression }

		ExprList anExprList = new ExprList();
		anExprList.add(expr());
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			anExprList.add(expr());
		}
		return anExprList;
	}

	private Expr expr() {

		Expr left = simpleExpr();
		Symbol op = lexer.token;
		if ( op == Symbol.EQ || op == Symbol.NEQ || op == Symbol.LE
				|| op == Symbol.LT || op == Symbol.GE || op == Symbol.GT ) {
			lexer.nextToken();
			Expr right = simpleExpr();
			
			if ( op == Symbol.EQ || op == Symbol.NEQ){
				if(!left.getType().isCompatible(right.getType()))
					signalError.showError("Incompatible types in comparison");
			}
			else{
				if(left.getType() != Type.intType || right.getType() != Type.intType)
						signalError.showError("Incompatible types in comparison");
			}
			
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

	private Expr simpleExpr() {
		Symbol op;

		Expr left = term();
		while ((op = lexer.token) == Symbol.MINUS || op == Symbol.PLUS
				|| op == Symbol.OR) {
			lexer.nextToken();
			Expr right = term();
			
			if(op == Symbol.OR){
				if(left.getType() != Type.booleanType || right.getType() != Type.booleanType)
					signalError.showError("Non boolean type expression in or operation");
			}
			else
				if(left.getType() != Type.intType || right.getType() != Type.intType)
					signalError.showError("Non int type expression in " + op.toString() + " operation");
					
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

	private Expr term() {
		Symbol op;

		Expr left = signalFactor();
		while ((op = lexer.token) == Symbol.DIV || op == Symbol.MULT
				|| op == Symbol.AND) {
			lexer.nextToken();
			Expr right = signalFactor();
			
			if(op == Symbol.AND){
				if(left.getType() != Type.booleanType || right.getType() != Type.booleanType)
					signalError.showError("Non boolean type expression in and operation");
			}
			else
				if(left.getType() != Type.intType || right.getType() != Type.intType)
					signalError.showError("Non int type expression in " + op.toString() + " operation");
			
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

	private Expr signalFactor() {
		Symbol op;
		if ( (op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS ) {
			lexer.nextToken();
			Expr expr;
			expr = factor();
			if(expr.getType() != Type.intType)
				signalError.showError("Only int expressions can be signed.");
			return new SignalExpr(op, expr);
		}
		else
			return factor();
	}

	private Expr factor() {

		Expr anExpr;
		ExprList exprList;
		String messageName, id;

		switch (lexer.token) {
		// IntValue
		case LITERALINT:
			return literalInt();
			// BooleanValue
		case FALSE:
			lexer.nextToken();
			return LiteralBoolean.False;
			// BooleanValue
		case TRUE:
			lexer.nextToken();
			return LiteralBoolean.True;
			// StringValue
		case LITERALSTRING:
			String literalString = lexer.getLiteralStringValue();
			lexer.nextToken();
			return new LiteralString(literalString);
			// "(" Expression ")" |
		case LEFTPAR:
			lexer.nextToken();
			anExpr = expr();
			if ( lexer.token != Symbol.RIGHTPAR ) 
				signalError.showError(") expected");
			lexer.nextToken();
			return new ParenthesisExpr(anExpr);

			// "null"
		case NULL:
			lexer.nextToken();
			return new NullExpr();
			// "!" Factor
		case NOT:
			lexer.nextToken();
			anExpr = expr();
			
			if(anExpr.getType() != Type.booleanType)
				signalError.showError("'not' operand is only allowed for boolean expressions");
			
			return new UnaryExpr(anExpr, Symbol.NOT);
			// ObjectCreation ::= "new" Id "(" ")"
		case NEW:
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");

			String className = lexer.getStringValue();
			KraClass objClass = null;
			if((objClass = symbolTable.getInGlobal(className)) == null)
				signalError.showError("Class " + className + " doesn't exists");
			lexer.nextToken();
			
			if ( lexer.token != Symbol.LEFTPAR ) 
				signalError.showError("( expected");
			lexer.nextToken();
			if ( lexer.token != Symbol.RIGHTPAR ) 
				signalError.showError(") expected");
			lexer.nextToken();
			/*
			 * return an object representing the creation of an object
			 */
			return new Obj(objClass, true);
			/*
          	 * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 Id  |
          	 *                 Id "." Id | 
          	 *                 Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 "this" | 
          	 *                 "this" "." Id | 
          	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
			 */
		case SUPER:
			// "super" "." Id "(" [ ExpressionList ] ")"
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				signalError.showError("'.' expected");
			}
			else
				lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");
			messageName = lexer.getStringValue();
			/*
			 * para fazer as confer�ncias sem�nticas, procure por 'messageName'
			 * na superclasse/superclasse da superclasse etc
			 */
			KraClass superClass = currentClass.getSuperclass();
			
			Method method = null;
			while(superClass != null){
				if((method = superClass.searchPublicMethod(messageName)) != null)
					break;
				superClass = superClass.getSuperclass();
			}
			
			if(superClass == null)
				signalError.showError("Public Method " + messageName + " not founded in any superclass of " + currentClass.getName());
			
			lexer.nextToken();
			exprList = realParameters();
			ParamList paramList = method.getParamList();
			
			if(exprList.getSize() != paramList.getSize())
				signalError.showError("Message " +  messageName + " have invalid number of parameters");
			
			Iterator<Parameter> paramIt = paramList.elements();
			Iterator<Expr> exprIt = exprList.getElements();
			
			while(paramIt.hasNext())
				if(!paramIt.next().getType().isCompatible(exprIt.next().getType()))
					signalError.showError("Message " + messageName + " expressions are not compatible with the expected parameters");
			
			return new MessageSendToSuper(method, exprList);
		case IDENT:
			/*
          	 * PrimaryExpr ::=  
          	 *                 Id  |
          	 *                 Id "." Id | 
          	 *                 Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
			 */

			String firstId = lexer.getStringValue();
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				// Id
				// retorne um objeto da ASA que representa um identificador
				Variable v = null;
				if((v = symbolTable.getInLocal(firstId)) == null)
					signalError.showError("Identifier " + firstId + " was not declared in this scope");
				return new VariableExpr(v);
			}
			else { // Id "."
				lexer.nextToken(); // coma o "."
				if ( lexer.token != Symbol.IDENT ) {
					signalError.showError("Identifier expected");
				}
				else {
					// Id "." Id
					
					lexer.nextToken();
					id = lexer.getStringValue();
					if ( lexer.token == Symbol.DOT ) {
						signalError.showError("Syntax error id.id.");
					}
					else if ( lexer.token == Symbol.LEFTPAR ) {
						// Id "." Id "(" [ ExpressionList ] ")"
						Variable v = (Variable) symbolTable.getInLocal(firstId);
						if(v == null)
							signalError.showError("Identifier " + firstId + " was not declared.");
						Type typeVar = v.getType();
						if(!(typeVar instanceof KraClass))
							signalError.showError("Attempt to call a method on a basic type variable");
						KraClass classVar = symbolTable.getInGlobal(typeVar.getName()); 
						Method meth = null;
						while(classVar != null){
							if ((meth = classVar.searchPublicMethod(id)) != null)
								break;
							classVar = classVar.getSuperclass();
						}
						
						if(meth == null)
							signalError.showError("Method " + id + " was not found in class " + typeVar.getName() + " or its superclasses");
						
						exprList = this.realParameters();
						paramList = meth.getParamList();
						
						if(exprList.getSize() != paramList.getSize())
							signalError.showError("Message to" +  firstId + " have invalid number of parameters");
						
						 paramIt = paramList.elements();
						 exprIt = exprList.getElements();
						
						while(paramIt.hasNext())
							if(!paramIt.next().getType().isCompatible(exprIt.next().getType()))
								signalError.showError("Message to " + firstId + " expressions are not compatible with the expected parameters");
						
						return new MessageSendToVariable(v, meth, exprList);
					}
					else {
						signalError.showError("Attempt to get external access to a private variable");
					}
				}
			}
			break;
		case THIS:
			/*
			 * Este 'case THIS:' trata os seguintes casos: 
          	 * PrimaryExpr ::= 
          	 *                 "this" | 
          	 *                 "this" "." Id | 
          	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
			 */
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				return new MessageSendToSelf(currentClass);
			}
			else {
				lexer.nextToken();
				if ( lexer.token != Symbol.IDENT )
					signalError.showError("Identifier expected");
				id = lexer.getStringValue();
				lexer.nextToken();
				// j� analisou "this" "." Id
				if ( lexer.token == Symbol.LEFTPAR ) {
					// "this" "." Id "(" [ ExpressionList ] ")"
					/*
					 * Confira se a classe corrente possui um m�todo cujo nome �
					 * 'ident' e que pode tomar os par�metros de ExpressionList
					 */
					Method meth = null;
					KraClass searchClass = currentClass;
					while(searchClass != null){
						if ((meth = searchClass.searchMethod(id)) != null)
							break;
						searchClass = searchClass.getSuperclass();
					}
					if(meth == null)
						signalError.showError("The method " + id + "is undefined for class " + currentClass.getName());
					
					exprList = this.realParameters();
					paramList = meth.getParamList();
					
					if(exprList.getSize() != paramList.getSize())
						signalError.showError("Wrong number of expressions to call method " + id);
					
					 paramIt = paramList.elements();
					 exprIt = exprList.getElements();
					
					while(paramIt.hasNext())
						if(!paramIt.next().getType().isCompatible(exprIt.next().getType()))
							signalError.showError("Wrong type of expressions to call method " + id);
					
					return new MessageSendToSelf(currentClass,meth, exprList);
				}
				else if ( lexer.token == Symbol.DOT ) {
					// "this" "." Id "." Id "(" [ ExpressionList ] ")"
					lexer.nextToken();
					if ( lexer.token != Symbol.IDENT )
						signalError.showError("Identifier expected");
					String secondId = lexer.getStringValue();  
					lexer.nextToken();
					
					Variable v =  currentClass.getVar(id);
					if(v == null)
						signalError.showError("Instance variable " + id + "is not declared in this class.");	
					Type typeVar = v.getType();
					if(!(typeVar instanceof KraClass))
						signalError.showError("Attempt to call a method on a basic type variable");
					KraClass classVar = (KraClass) typeVar;
					Method meth = classVar.searchPublicMethod(secondId);
					if(meth == null)
						signalError.showError("The method " + secondId + "is undefined for class " + classVar.getName());
					exprList = this.realParameters();
					paramList = meth.getParamList();
					
					if(exprList.getSize() != paramList.getSize())
						signalError.showError("Message to" +  id + " have invalid number of parameters");
					paramIt = paramList.elements();
					exprIt = exprList.getElements();
					
					while(paramIt.hasNext())
						if(!paramIt.next().getType().isCompatible(exprIt.next().getType()))
							signalError.showError("Message to " + id + " expressions are not compatible with the expected parameters");
					
					return new MessageSendToSelf(currentClass, v, meth, exprList);
				}
				else {
					// retorne o objeto da ASA que representa "this" "." Id
					/*
					 * confira se a classe corrente realmente possui uma
					 * vari�vel de inst�ncia 'ident'
					 */
					Variable v = currentClass.getVar(id);
					if(v == null)
						signalError.showError("Instance Variable " + id + "is not declared in this class");
					return new MessageSendToSelf(currentClass, v);
					
				}
			}
		default:
			signalError.showError("Expression expected");
		}
		return null;
	}

	private LiteralInt literalInt() {
		// the number value is stored in lexer.getToken().value as an object of
		// Integer.
		// Method intValue returns that value as an value of type int.
		int value = lexer.getNumberValue();
		lexer.nextToken();
		return new LiteralInt(value);
	}

	private static boolean startExpr(Symbol token) {

		return token == Symbol.FALSE || token == Symbol.TRUE
				|| token == Symbol.NOT || token == Symbol.THIS
				|| token == Symbol.LITERALINT || token == Symbol.SUPER
				|| token == Symbol.LEFTPAR || token == Symbol.NULL
				|| token == Symbol.IDENT || token == Symbol.LITERALSTRING;

	}

}
