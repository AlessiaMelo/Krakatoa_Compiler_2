//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483
package ast;

import java.util.*;

public class ParamList {

	private ArrayList<Parameter> paramList;

	public ParamList() {
		paramList = new ArrayList<Parameter>();
	}

	public void add(Parameter v) {
		paramList.add(v);
	}

	public Iterator<Parameter> elements() {
		return paramList.iterator();
	}

	public int getSize() {
		return paramList.size();
	}

	public void genKra(PW pw) {
		int size = paramList.size();
<<<<<<< HEAD
		for (Parameter p : paramList) {
			p.genKra(pw);
			if (--size > 0)
				pw.print(", ");
		}
	}

	public void genCpp(PW pw) {
		int size = paramList.size();
		for (Parameter p : paramList) {
			p.genCpp(pw);
			if (--size > 0)
				pw.print(", ");
		}
=======
        for ( Parameter p : paramList ) {
            p.genKra(pw);
            if ( --size > 0 )
                pw.print(", ");
        }
	}
	
	public void genCpp(PW pw){
		int size = paramList.size();
        for ( Parameter p : paramList ) {
            p.genCpp(pw);
            if ( --size > 0 )
                pw.print(", ");
        }
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
	}
}