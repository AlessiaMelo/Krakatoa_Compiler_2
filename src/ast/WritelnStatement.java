//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class WritelnStatement extends Statement {

    private ExprList exprList;

    public WritelnStatement (ExprList exprList){
        this.exprList = exprList;
    }

    @Override
    public void genKra(PW pw){
    	pw.printIdent("writeln(");
        this.exprList.genKra(pw);
        pw.println(");");
    }

    @Override
    public void genC(PW pw) {

    }
}