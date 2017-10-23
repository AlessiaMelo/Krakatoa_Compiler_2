//Al�ssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class NullExpr extends Expr {
	
	private Type type;
	
	public void genC( PW pw, boolean putParenthesis ) {
		pw.printIdent("NULL");
	}

	public Type getType() {
		return type;
	}

	public NullExpr (){
		this.type = Type.undefinedType;
	}

	@Override
	public void genKra(PW pw, boolean parenthesis) {
		pw.print("null");
	}

}