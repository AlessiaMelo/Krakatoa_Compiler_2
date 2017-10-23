//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

//add extends
public class ReturnStatement extends Statement {

	private Expr returnExpr;
	
	public ReturnStatement(Expr e) {
		returnExpr = e;
	}
	
	@Override
    public void genKra(PW pw){
        pw.printIdent("return ");
        this.returnExpr.genKra(pw, false);
        pw.println(";");
    }

    @Override
    public void genC(PW pw) {

    }
	
}
