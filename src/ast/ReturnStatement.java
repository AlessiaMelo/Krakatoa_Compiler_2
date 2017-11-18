<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
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

<<<<<<< HEAD
	@Override
=======
    @Override
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
    public void genCpp(PW pw) {
        pw.printIdent("return ");
        this.returnExpr.genCpp(pw, false);
        pw.println(";");
    }
	
}
