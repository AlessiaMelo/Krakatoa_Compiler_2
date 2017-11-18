//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class VariableExpr extends Expr {
<<<<<<< HEAD

	private Variable v;

	public VariableExpr(Variable v) {
		this.v = v;
	}

	public void genC(PW pw, boolean putParenthesis) {
		pw.print(v.getName());
	}

	public Type getType() {
		return v.getType();
	}
=======
	
    private Variable v;
    
    public VariableExpr( Variable v ) {
        this.v = v;
    }
    
    public void genCpp( PW pw, boolean putParenthesis ) {
    	if(putParenthesis)
			pw.print("(");
        pw.print(v.getName());
        if(putParenthesis)
        	pw.print(")");
    }
    
    public Type getType() {
        return v.getType();
    }
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992

	@Override
	public void genKra(PW pw, boolean putParenthesis) {
		if (putParenthesis)
			pw.print("(");
		pw.print(v.getName());
		if (putParenthesis)
			pw.print(")");
	}

	public void genCpp(PW pw, boolean putParenthesis) {
		if (putParenthesis)
			pw.print("(");
		pw.print(v.getName());
		if (putParenthesis)
			pw.print(")");
	}

}