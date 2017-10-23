//Al�ssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

import lexer.*;
import java.util.HashMap;


public class CompositeExpr extends Expr {

	//SimpleExpression ::= Term { LowOperator Term }

	public CompositeExpr( Expr pleft, Symbol poper, Expr pright ) {
		left = pleft;
		oper = poper;
		right = pright;
	}

	@Override
	public void genCpp( PW pw, boolean putParenthesis ) {
		if ( putParenthesis )
			pw.print("(");
		left.genCpp(pw, true);
		String strSymbol = arrayOper.get(oper);
		if ( strSymbol == null ) {
			pw.println("internal error in CompositeExpr::genCpp");
		}
		else
			pw.print(" " + strSymbol + " ");
		right.genCpp(pw, true);
		if ( putParenthesis )
			pw.print(")");
	}

	public void genKra(PW pw, boolean putParenthesis) {
		
		if ( putParenthesis )
		{
			pw.print("(");
		}
		//true or false for parentesis		
		
		left.genKra(pw,false);
		///pw.println("" + right.getClass());
		String strSymbol = arrayOper.get(oper);
		if ( strSymbol == null ) {
			//Symbol not recognized
			pw.println("internal error in CompositeExpr::genKra");
		}
		else
		{
			pw.print(" "+ strSymbol + " ");
		}
		
		right.genKra(pw, false);
		if ( putParenthesis ) {
			pw.print(")");
		}

	}

	@Override
	public Type getType() {
		// left and right must be the same type
		if ( oper == Symbol.EQ || oper == Symbol.NEQ || oper == Symbol.LE || oper == Symbol.LT ||
				oper == Symbol.GE || oper == Symbol.GT )
			return Type.booleanType;
		else if ( oper == Symbol.AND || oper == Symbol.OR )
			return Type.booleanType;
		else
			return Type.intType;
	}

	private Expr left, right;
	private Symbol oper;
	private static HashMap<Symbol, String> arrayOper;
	static {
		arrayOper = new HashMap<Symbol, String>();
		arrayOper.put(Symbol.PLUS, "+");
		arrayOper.put(Symbol.MINUS, "-");
		arrayOper.put(Symbol.MULT, "*");
		arrayOper.put(Symbol.DIV, "/");
		arrayOper.put(Symbol.LT, "<");
		arrayOper.put(Symbol.LE, "<=");
		arrayOper.put(Symbol.GT, ">");
		arrayOper.put(Symbol.GE, ">=");
		arrayOper.put(Symbol.NEQ, "!=");
		arrayOper.put(Symbol.EQ, "==");
		arrayOper.put(Symbol.ASSIGN, "=");
		arrayOper.put(Symbol.AND, "&&");
		arrayOper.put(Symbol.OR, "||");
	}
	
	
}
