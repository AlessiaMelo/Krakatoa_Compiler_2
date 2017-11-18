//Laboratório de Compiladores - fase  1
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

import java.util.ArrayList;


public class ReadStatement extends Statement{
	//ReadStat ::= \read" \(" LeftValue f \," LeftValue g \)"
	private ArrayList<Variable> readVarList;
	private ArrayList<Boolean> messages;
	
	
	public ReadStatement() {
		readVarList = new ArrayList<Variable>();
		messages = new ArrayList<Boolean>();
	}
	
	public void addVar(Variable v){
		readVarList.add(v);
	}
	
	public void addMessage(boolean msg){
		messages.add(msg);
	}

	@Override
	public void genCpp(PW pw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genKra(PW pw) {
		pw.printIdent("read(");
		int size = readVarList.size();
		int i = 0;
		for ( Variable v : readVarList ) {
			if(messages.get(i) == Boolean.TRUE)
				pw.print("this.");
        	pw.print(v.getName());        	
            if ( --size > 0 )
                pw.print(", ");
            i++;
        }
        pw.print(");");
        pw.println();
	}
}


