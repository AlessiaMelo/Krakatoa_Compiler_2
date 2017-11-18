//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483
package ast;

public class LiteralInt extends Expr {
	
	//BasicValue ::= IntValue | BooleanValue | StringValue
	
	private int value;

	public LiteralInt( int value ) { 
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public void genCpp( PW pw, boolean parenthesis ) {
		if(parenthesis)
		{
			pw.print("(");
		}
		pw.printIdent("" + value);
		if(parenthesis)
		{
			pw.print(")");
		}
	}

	public Type getType() {
		return Type.intType;
	}

	@Override
	public void genKra(PW pw, boolean parenthesis) {
		if(parenthesis)
		{
			pw.print("(");
		}
		pw.print("" + this.value);        
		if(parenthesis)
		{
			pw.print(")");
		}
	}
}
