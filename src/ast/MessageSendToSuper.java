//Laboratório de Compiladores - fase 1 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483
package ast;

public class MessageSendToSuper extends MessageSend { 
	Method method;
	ExprList exprList;
	
    public MessageSendToSuper(Method method, ExprList exprList) {
		this.method = method;
		this.exprList = exprList;
	}

	public Type getType() { 
        return method.getReturnType();
    }

    public void genC( PW pw, boolean putParenthesis ) {
        
    }

	@Override
	public void genKra(PW pw, boolean putParenthesis) {
		if(putParenthesis)
		{
			pw.print("(");
		}
		pw.print("super." + method.getName() + "(");
		if(exprList != null)
			exprList.genKra(pw);
		pw.print(")");
		if(putParenthesis)
		{
			pw.print(")");
		}
		
		
	}
    
}