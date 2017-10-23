package ast;

public class DoWhileStatement extends Statement {
	Statement commands;
	Expr cond;

	public DoWhileStatement(Statement s, Expr e) {
		commands = s;
		cond = e;
	}

	@Override
	public void genCpp(PW pw) {
		// No keys here, only on composite stmt, it includes identation
		pw.printIdent("do ");
		commands.genCpp(pw);
		pw.printIdent("while(");
		cond.genCpp(pw, false);
		pw.println(");");

	}

	@Override
	public void genKra(PW pw) {
		// No keys here, only on composite stmt, it includes identation
		pw.printIdent("do ");
		commands.genKra(pw);
		pw.printIdent("while(");
		cond.genKra(pw, false);
		pw.println(");");

	}

}
