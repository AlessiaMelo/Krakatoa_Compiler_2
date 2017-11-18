<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

package ast;

import lexer.*;

public class SignalExpr extends Expr {
<<<<<<< HEAD
	private Expr expr;
	private Symbol oper;

	public SignalExpr(Symbol oper, Expr expr) {
		this.oper = oper;
		this.expr = expr;
	}

	@Override
	public void genCpp(PW pw, boolean putParenthesis) {
		if (putParenthesis)
			pw.print("(");
		pw.print(oper == Symbol.PLUS ? "+" : "-");
		expr.genCpp(pw, true);
		if (putParenthesis)
			pw.print(")");
	}

	@Override
	public Type getType() {
		return expr.getType();
	}

	@Override
	public void genKra(PW pw, boolean putParenthesis) {
		if (putParenthesis) {
			pw.print("(");
		}
		if (oper == Symbol.PLUS) {
			pw.print("+");
		} else {
			pw.print("-");
		}
		expr.genKra(pw, true);
		if (putParenthesis) {
			pw.print(")");
		}

	}
=======
    private Expr expr;
    private Symbol oper;
	
    public SignalExpr( Symbol oper, Expr expr ) {
       this.oper = oper;
       this.expr = expr;
    }

    @Override
	public void genCpp( PW pw, boolean putParenthesis ) {
       if ( putParenthesis )
          pw.print("(");
       pw.print( oper == Symbol.PLUS ? "+" : "-" );
       expr.genCpp(pw, true);
       if ( putParenthesis )
          pw.print(")");
    }

    @Override
	public Type getType() {
       return expr.getType();
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if ( putParenthesis )
            pw.print("(");
        if  (oper == Symbol.PLUS)
        	pw.print("+");
        else
        	pw.print("-");        
        expr.genKra(pw, true);
        if ( putParenthesis )
        	 pw.print(")");
    }
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992

}
