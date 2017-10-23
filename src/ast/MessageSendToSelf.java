package ast;


public class MessageSendToSelf extends MessageSend {
    KraClass messageClass;
    Variable instVar;
    Method messageMeth;
    ExprList params;
	
	
    public MessageSendToSelf(KraClass currentClass, Variable v, Method meth,
			ExprList exprList) {
		messageClass = currentClass;
		instVar = v;
		messageMeth = meth;
		params = exprList;
	}

	public MessageSendToSelf(KraClass currentClass, Variable v) {
		messageClass = currentClass;
		instVar = v;
		messageMeth = null;
		params = null;
	}

	public MessageSendToSelf(KraClass currentClass, Method meth, ExprList exprList) {
		messageClass = currentClass;
		instVar = null;
		messageMeth = meth;
		params = exprList;
	}

	public MessageSendToSelf(KraClass currentClass) {
		messageClass = currentClass;
		instVar = null;
		messageMeth = null;
		params = null;
	}

	public Type getType() { 
		if(messageMeth != null)
			return messageMeth.getReturnType();
		else if(instVar != null)
			return instVar.getType();
		else
			return messageClass;
		
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
    }

	@Override
	public void genKra(PW pw, boolean putParenthesis) {
		if(putParenthesis)
		{
			pw.print("(");
		}
		pw.print("this"); 
		if(instVar != null){
			pw.print("." + instVar.getName());
		}
		if(messageMeth != null){
			pw.print("." + messageMeth.getName() + "(");
			if(params != null)
				params.genKra(pw);
			pw.print(")");
		}
		if(putParenthesis)
		{
			pw.print(")");
		}
		
	}
    
    
}