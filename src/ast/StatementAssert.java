<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

//what is this???
package ast;

public class StatementAssert extends Statement {

	private Expr expr;
	private int lineNumber;
	private String message;

	public StatementAssert(Expr expr, int lineNumber, String message) {
		this.expr = expr;
		this.lineNumber = lineNumber;
		this.message = message;
	}

	@Override
	public void genCpp(PW pw) {
		pw.printIdent("if ( !( ");
		expr.genCpp(pw, false);
		pw.println(" ) ) {");
		pw.add();
<<<<<<< HEAD
		pw.printlnIdent("cout << \"" + message + "\";");
=======
		pw.printlnIdent("cout << \"" + message +  "\";");
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
		pw.sub();
		pw.printlnIdent("}");
	}

	public Expr getExpr() {
		return expr;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void genKra(PW pw) {
<<<<<<< HEAD

		pw.printIdent("assert ");
=======
		pw.printIdent("assert ");	
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
		expr.genKra(pw, false);
		pw.print(", ");
		pw.println("puts(\"" + message + "\");");
	}
}
