<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

package ast;

public class ParenthesisExpr extends Expr {
    
	private Expr expr;
	
    public ParenthesisExpr( Expr expr ) {
        this.expr = expr;
    }
    
    public void genCpp( PW pw, boolean putParenthesis ) {
        pw.print("(");
        expr.genCpp(pw, false);
        pw.printIdent(")");
    }
    
    public Type getType() {
        return expr.getType();
    }

    @Override
	public void genKra(PW pw, boolean putParenthesis) {
        pw.print("(");
        expr.genKra(pw, false);
        pw.print(")");
    }
    
}