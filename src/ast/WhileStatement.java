//Aléssia Melo 		RA: 620289
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
	public void genC(PW pw) {
		
	}
	
	//Here are the { } comp Stmt
	@Override
	public void genKra(PW pw) {
		//No keys here, only on composite stmt, it includes identation
		
		pw.printIdent("while(");
		cond.genKra(pw, false);		
		pw.print(")");		
		commands.genKra(pw);
	
		
	}
	
}