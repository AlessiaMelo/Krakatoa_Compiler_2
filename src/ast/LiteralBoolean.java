//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class LiteralBoolean extends Expr {

	//BooleanValue ::= \true" | \false"
	private boolean value;
	
	public static LiteralBoolean True  = new LiteralBoolean(true);
	public static LiteralBoolean False = new LiteralBoolean(false);
	

	public LiteralBoolean( boolean value ) {
		this.value = value;
	}

	@Override
	public void genCpp( PW pw, boolean parenthesis ) {
		if(parenthesis)
		{
			pw.print("(");
		}
		pw.print( value ? "1" : "0" );
		if(parenthesis)
		{
			pw.print(")");
		}
		
		
	}

	@Override
	public Type getType() {
		return Type.booleanType;
	}

	@Override
	public void genKra(PW pw, boolean parenthesis) {
		if(parenthesis)
		{
			pw.print("(");
		}
		if(this.value)
		{
			pw.print("true");
		}
		else
		{
			pw.print("false");
		}
		if(parenthesis)
		{
			pw.print(")");
		}

	}


}
