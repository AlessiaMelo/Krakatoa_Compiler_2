//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483
package ast;

import java.util.*;

public class LocalVariableList {
	//LocalDec ::= Type IdList \;"
	private ArrayList<Variable> localList;

    public LocalVariableList() {
       localList = new ArrayList<Variable>();
    }

    public void addElement(Variable v) {
       localList.add(v);
    }

    public Iterator<Variable> elements() {
        return localList.iterator();
    }

    public int getSize() {
        return localList.size();
    }
    
    public void genKra(PW pw) {

    	//printing type and variable
        for (Variable variable : localList) {
        	pw.printIdent(variable.getType().getName());
            pw.print(" ");
            pw.print(variable.getName());
            pw.println(";");
        }
        pw.println("");
    }
    
    public void genCpp(PW pw) {

    	//printing type and variable
        for (Variable variable : localList) {
        	pw.printIdent(variable.getType().getName());
            pw.print(" ");
            pw.print(variable.getName());
            pw.println(";");
        }
        pw.println("");
    }


    

}
