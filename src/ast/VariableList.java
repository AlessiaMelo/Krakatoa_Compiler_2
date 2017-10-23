package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class VariableList {
	private ArrayList<Variable> varList;
	
	 public VariableList() {
	       varList = new ArrayList<Variable>();
	    }

	    public void add(Variable v) {
	       varList.add( v );
	    }

	    public Iterator<Variable> elements() {
	    	return varList.iterator();
	    }

	    public int getSize() {
	        return varList.size();
	    }

		public void genKra(PW pw) {
			int size = varList.size();
	        for ( Variable v : varList ) {
	        	///pw.println("TEM ALGO varlist");
	            v.genKra(pw);
	            //if ( --size > 0 )
	                //pw.print(", ");
	        }
			
		}
}
