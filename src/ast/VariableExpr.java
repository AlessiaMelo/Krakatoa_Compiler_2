//Al�ssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class VariableExpr extends Expr {
	
    private Variable v;
    
    public VariableExpr( Variable v ) {
        this.v = v;
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
        pw.print( v.getName() );
    }
    
    public Type getType() {
        return v.getType();
    }

	@Override
	 public void genKra( PW pw, boolean putParenthesis ) {
		if(putParenthesis)
			pw.print("(");
        pw.print(v.getName() );
        if(putParenthesis)
        	pw.print(")");
    }
    
}