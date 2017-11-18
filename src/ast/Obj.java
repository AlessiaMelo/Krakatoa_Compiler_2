//Laboratório de Compiladores - fase 1 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class Obj extends Expr{
	
	private KraClass kraClass;
    private boolean newObj;

    public Obj (KraClass kraClass, boolean newObj){
        this.kraClass = kraClass;
        this.newObj = newObj;
    }
    
    @Override
    public Type getType() {
        return kraClass;
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if(putParenthesis)
        {
            pw.print("(");
        }
        if(this.newObj){
            pw.print("new ");
            pw.print(this.kraClass.getName());
            pw.print("()");
        }
        else
        {
            pw.print("this");
        }
        if(putParenthesis)
        {
            pw.print(")");
        }
    }

	@Override
	public void genC(PW pw, boolean putParenthesis) {
		// TODO Auto-generated method stub
		
	}
}
