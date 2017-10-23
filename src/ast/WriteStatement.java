//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class WriteStatement extends Statement {

    private ExprList exprList;

    public WriteStatement (ExprList exprList){
        this.exprList = exprList;
    }

    @Override
    public void genKra(PW pw){
    	
        pw.printIdent("write(");
        this.exprList.genKra(pw);
        pw.println(");");
    }

    @Override
    public void genC(PW pw) {

    }
}
