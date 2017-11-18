//Laboratório de Compiladores - fase 1 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483
package ast;

import java.awt.print.Printable;


public class MessageSendToVariable extends MessageSend { 
	Variable receptor;
	Method method;
	ExprList exprList;
	
    public MessageSendToVariable(Variable v, Method method, ExprList exprList) {
		receptor = v;
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
		pw.print(receptor.getName() + "." + method.getName() + "(");
		if(exprList != null)
			exprList.genKra(pw);
		pw.print(")");
		if(putParenthesis)
		{
			pw.print(")");
		}
			
	}

    
}    