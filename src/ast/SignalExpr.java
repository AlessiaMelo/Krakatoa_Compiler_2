//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

import lexer.*;

public class SignalExpr extends Expr {
    private Expr expr;
    private Symbol oper;
	
    public SignalExpr( Symbol oper, Expr expr ) {
       this.oper = oper;
       this.expr = expr;
    }

    @Override
	public void genC( PW pw, boolean putParenthesis ) {
       if ( putParenthesis )
          pw.print("(");
       pw.print( oper == Symbol.PLUS ? "+" : "-" );
       expr.genC(pw, true);
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
        {
            pw.print("(");
        }
        if  (oper == Symbol.PLUS)
        {
        	pw.print("+");
        }
        else
        {
        	pw.print("-");
        }        
        expr.genKra(pw, true);
        if ( putParenthesis )
        {
        	 pw.print(")");
        }
           
    }

}
