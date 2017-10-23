//Alessia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483
package ast;

import java.util.*;

public class InstanceVariableList {

	private ArrayList<InstanceVariable> instanceVariableList;

	//static instance ainda n�o precisa

	public InstanceVariableList() {
		instanceVariableList = new ArrayList<InstanceVariable>();
	}

	public void addElement(InstanceVariable instanceVariable) {
		instanceVariableList.add( instanceVariable );
	}

	public Iterator<InstanceVariable> elements() {
		return this.instanceVariableList.iterator();
	}

	public int getSize() {
		return instanceVariableList.size();
	}

	public void genKra(PW pw) {
		for (InstanceVariable instanceVariable: instanceVariableList)
			instanceVariable.genKra(pw);
		//if(!this.instanceVariableList.isEmpty())
			//pw.println("");
	}
	
	public void genCpp(PW pw) {
		for (InstanceVariable instanceVariable: instanceVariableList)
			instanceVariable.genCpp(pw);
		
	}


}
