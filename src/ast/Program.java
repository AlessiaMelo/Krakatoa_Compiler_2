<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

package ast;

import java.util.*;
import comp.CompilationError;

public class Program {

	public Program(ArrayList<KraClass> classList, ArrayList<MetaobjectCall> metaobjectCallList, 
			       ArrayList<CompilationError> compilationErrorList) {
		this.classList = classList;
		this.metaobjectCallList = metaobjectCallList;
		this.compilationErrorList = compilationErrorList;
	}


	public void genKra(PW pw) {
		for(KraClass c: classList)
			c.genKra(pw);
	}


	public void genCpp(PW pw) {
		pw.println("#include<bits/stdc++.h>\n");
		pw.println("using namespace std;\n");
		
		for(KraClass c: classList)
			c.genCpp(pw);
		
		pw.println("int main(){");
		pw.println("Program p;");
		pw.println("p.run();");
		pw.println("return 0; \n}");
	}
	
	public ArrayList<KraClass> getClassList() {
		return classList;
	}


	public ArrayList<MetaobjectCall> getMetaobjectCallList() {
		return metaobjectCallList;
	}
	

	public boolean hasCompilationErrors() {
		return compilationErrorList != null && compilationErrorList.size() > 0 ;
	}

	public ArrayList<CompilationError> getCompilationErrorList() {
		return compilationErrorList;
	}

	
	private ArrayList<KraClass> classList;
	private ArrayList<MetaobjectCall> metaobjectCallList;
	
	ArrayList<CompilationError> compilationErrorList;

	
}