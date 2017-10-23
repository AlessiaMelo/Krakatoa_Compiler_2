//Alessia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

//add extends
public class CompositeStatement extends Statement {
	// CompStatement ::= "{" { Statement } "}"
	// StatementList ::= "{" { Statement } "}"

	private StatementList stmtList;

	public CompositeStatement(StatementList stmtList) {
		this.stmtList = stmtList;
	}

	public void genKra(PW pw) {
		pw.println();
		pw.printlnIdent("{");
		pw.add();
		stmtList.genKra(pw);
		pw.sub();
		pw.printlnIdent("}");
	}

	@Override
	public void genCpp(PW pw) {
		pw.println();
		pw.printlnIdent("{");
		pw.add();
		stmtList.genCpp(pw);
		pw.sub();
		pw.printlnIdent("}");
	}

}
