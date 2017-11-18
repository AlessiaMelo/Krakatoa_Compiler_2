//Laboratório de Compiladores - fase 1 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class MessageSendStatement extends Statement { 
	
	
	private MessageSend  messageSend;
	
	public void genC( PW pw ) {
		pw.printIdent("");
		// messageSend.genC(pw);
		pw.println(";");
	}

	public void genKra(PW pw) {
		pw.printIdent("");
		this.messageSend.genKra(pw, false);
		pw.println(";");
	}



}


