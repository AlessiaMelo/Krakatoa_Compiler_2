//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483



package ast;

//add extend
public class BreakStatement extends Statement{
	
	@Override
	public void genCpp(PW pw) {
		 pw.printlnIdent("break;");		
	}
	
	public void genKra(PW pw){
        pw.printlnIdent("break;");
    }
	

}
