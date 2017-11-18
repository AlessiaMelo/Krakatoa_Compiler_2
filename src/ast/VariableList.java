//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class VariableList {
	private ArrayList<Variable> varList;
<<<<<<< HEAD

	public VariableList() {
		varList = new ArrayList<Variable>();
	}

	public void add(Variable v) {
		varList.add(v);
	}

	public Iterator<Variable> elements() {
		return varList.iterator();
	}

	public int getSize() {
		return varList.size();
	}

	public void genKra(PW pw) {
		int size = varList.size();
		for (Variable v : varList) {
			/// pw.println("TEM ALGO varlist");
			v.genKra(pw);
			// if ( --size > 0 )
			// pw.print(", ");
=======
	
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
	        for ( Variable v : varList ) 
	            v.genKra(pw);
		}
		public void genCpp(PW pw){
			for(Variable v : varList)
				v.genCpp(pw);
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
		}

	}

	public void genCpp(PW pw) {
		for (Variable v : varList)
			v.genCpp(pw);
	}
}
