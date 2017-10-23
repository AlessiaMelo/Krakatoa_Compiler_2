//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class AssignStatement extends Statement{

	//AssignExprLocalDec ::= Expression [ "=" Expression ] | LocalDec

	private Expr leftExpr, rightExpr;

	public AssignStatement (Expr leftExpr, Expr rightExpr){
		this.leftExpr = leftExpr;
		this.rightExpr = rightExpr;
	}


	public void genKra(PW pw){
		//new line
		pw.printIdent("");
		this.leftExpr.genKra(pw, false);
		//optional
		if(this.rightExpr != null){
			pw.print(" = ");
			
			this.rightExpr.genKra(pw, false);
		}
		pw.println(";");
	}

	@Override
	public void genCpp(PW pw) {
		// new line
		pw.printIdent("");
		this.leftExpr.genCpp(pw, false);
		// optional
		if (this.rightExpr != null) {
			pw.print(" = ");
			this.rightExpr.genCpp(pw, false);
		}
		pw.println(";");

	}

}