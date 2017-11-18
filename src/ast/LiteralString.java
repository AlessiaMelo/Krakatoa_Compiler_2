//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483
package ast;

public class LiteralString extends Expr {
	//BasicValue ::= IntValue | BooleanValue | StringValue
	private String literalString;
	
    public LiteralString( String literalString ) { 
        this.literalString = literalString;
    }
    
    public void genCpp(PW pw, boolean putParenthesis) {
		// if is empty
		if (this.literalString.equals("")) {
			pw.print("\"\"");
		} else {
			pw.print("\"" + this.literalString + "\"");
		}
	}
    
    public Type getType() {
        return Type.stringType;
    }

	@Override
	public void genKra(PW pw, boolean parenthesis) {
		//if is empty
		if(this.literalString.equals(""))
		{
			pw.print("\"\"");
		}
        else
        {
            pw.print("\""+  this.literalString +"\"");
        }
	}
    
    
}
