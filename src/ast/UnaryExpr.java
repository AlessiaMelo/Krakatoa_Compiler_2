<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

package ast;

import lexer.*;

public class UnaryExpr extends Expr {

	private Expr expr;
	private Symbol op;

	public UnaryExpr(Expr expr, Symbol op) {
		this.expr = expr;
		this.op = op;
	}

	@Override
	public void genCpp(PW pw, boolean putParenthesis) {
		switch (op) {
		case PLUS:
			pw.print("+");
			break;
		case MINUS:
			pw.print("-");
			break;
		case NOT:
			pw.print("!");
			break;
		default:
			pw.print(" internal error at UnaryExpr::genC");

		}
		expr.genCpp(pw, false);
	}

	@Override
	public Type getType() {
		return expr.getType();
	}

	@Override
	public void genKra(PW pw, boolean putParenthesis) {
		switch (op) {
		case PLUS:
			pw.print("+");
			break;
		case MINUS:
			pw.print("-");
			break;
		case NOT:
			pw.print("!");
			break;
		default:
			pw.print(" internal error at UnaryExpr::genKra");

		}
		expr.genKra(pw, false);
	}
}
