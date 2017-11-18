<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

package ast;

public class WhileStatement extends Statement{
	private Expr cond;
	private Statement commands;
	
	public WhileStatement(Expr e, Statement s) {
		cond = e;
		commands = s;
	}
	
	public Expr getCond() {
		return cond;
	}
	
	public Statement getCommands() {
		return commands;
	}
	
	@Override
	public void genCpp(PW pw) {
		pw.printIdent("while(");
		cond.genCpp(pw, false);
		pw.print(")");
		commands.genCpp(pw);
	}
	
	
	@Override
	public void genKra(PW pw) {
		//No keys here, only on composite stmt, it includes identation
		
		pw.printIdent("while(");
		cond.genKra(pw, false);		
		pw.println(")");		
		commands.genKra(pw);
	
		
	}
}