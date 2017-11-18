<<<<<<< HEAD
//Laboratório de Compiladores - fase 1 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

package ast;

import java.util.Iterator;

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

<<<<<<< HEAD

=======
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
    @Override
    public void genCpp(PW pw) {
    	pw.printIdent("cout");
    	Iterator<Expr> it = exprList.getElements();
    	while(it.hasNext()){
    		pw.print(" << ");
    		it.next().genCpp(pw, false);
    	}	
    	pw.println(" << endl;");
    }
}