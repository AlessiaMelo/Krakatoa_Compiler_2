//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

//add extend
public class BreakStatement extends Statement{
		
	public void genKra(PW pw){
        pw.printlnIdent("break;");
    }

	@Override
	public void genCpp(PW pw) {
		 pw.printlnIdent("break;");		
	}
	

}
