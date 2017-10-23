//Alessia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483
package ast;

import java.util.*;

public class ExprList extends Expr{
	
	
	private ArrayList<Expr> exprList;

	//ExpressionList ::= Expression "{" Expression "}"
    public ExprList() {
        exprList = new ArrayList<Expr>();
    }

    public void add( Expr expr ) {
        exprList.add(expr);
    }
   
	public int getSize()
	{
		return  exprList.size();
	}
    
	public Iterator<Expr> getElements(){
		return exprList.iterator();
	}
	
    public void genCpp( PW pw ) {
        int size = exprList.size();
        for ( Expr e : exprList ) {
        	e.genCpp(pw, false);
            if ( --size > 0 )
                pw.print(", ");
        }
    }
    
    
    public void genKra(PW pw){
        int size = exprList.size();
        for ( Expr e : exprList ) {
        	
            e.genKra(pw, false);
            if ( --size > 0 )
                pw.print(", ");
        }
    }
	
    ///Is it nedded?
    
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void genKra(PW pw, boolean putParenthesis) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genCpp(PW pw, boolean putParenthesis) {
		// TODO Auto-generated method stub
		
	}

	

}
