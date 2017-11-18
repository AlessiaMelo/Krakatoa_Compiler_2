//Laboratório de Compiladores - fase 1 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483


//more methods needed?
package ast;

import java.util.ArrayList;

import lexer.Symbol;

public class Method {

	private Type returnType;
	private String name;
	private Symbol qualifier;
	private ParamList paramList;
	private StatementList stmtList;
	private VariableList localVarList;
	
	public Method(Type type, String name, Symbol qualifier){
		returnType = type;
		this.name = name;
		this.qualifier = qualifier;
		paramList = new ParamList();
		stmtList = new StatementList();
		localVarList = new VariableList();
	}

	public ParamList getParamList() {
		return paramList;
	}

	public Type getReturnType() {
		return returnType;
	}

	public String getName() {
		return name;
	}

	public Symbol getQualifier() {
		return qualifier;
	}

	public void addStmtList(StatementList statementList) {
		stmtList = statementList;	
	}

	public void addVar(Variable v) {
		localVarList.add(v);
	}

	public void addParameter(Parameter p) {
		paramList.add(p);
		
	}
	
	public void genKra(PW pw) {
		
		pw.println();
       
        pw.printIdent(this.qualifier.toString());
        pw.print(" ");
        pw.print(this.returnType.getName());
        pw.print(" ");
        pw.print(this.name);
        pw.print("(");

        if(this.paramList != null) {        	
            this.paramList.genKra(pw);
        }
        pw.println(") {");
        pw.add();

        if(this.localVarList != null)
        {        	
            this.localVarList.genKra(pw);
        }
        if(this.stmtList != null)
        {        	
            this.stmtList.genKra(pw);
        }
        pw.sub();
        pw.printlnIdent("}");
        

    }
	
}
