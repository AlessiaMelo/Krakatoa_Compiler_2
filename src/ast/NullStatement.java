//Laboratório de Compiladores - fase 1 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

//what??

package ast;

public class NullStatement extends Statement{


	@Override
	public void genC(PW pw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genKra(PW pw) {
		pw.printlnIdent(";");
		
	}
}
