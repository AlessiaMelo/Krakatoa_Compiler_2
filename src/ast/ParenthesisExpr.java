//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class ParenthesisExpr extends Expr {
    
	private Expr expr;
	
    public ParenthesisExpr( Expr expr ) {
        this.expr = expr;
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
        pw.print("(");
        expr.genC(pw, false);
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