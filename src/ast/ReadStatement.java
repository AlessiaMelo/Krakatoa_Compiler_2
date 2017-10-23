//Al�ssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

import java.util.ArrayList;

//check: Why variable? not Expr?

public class ReadStatement extends Statement{
	//ReadStat ::= \read" \(" LeftValue f \," LeftValue g \)"
	private ArrayList<Variable> readVarList;
	
	public ReadStatement(ArrayList<Variable> readStmt) {
		readVarList = readStmt;
	}

	@Override
	public void genC(PW pw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genKra(PW pw) {
		pw.printIdent("read(");
		int size = readVarList.size();
		for ( Variable v : readVarList ) {
        	 pw.print(v.getName());        	
            if ( --size > 0 )
                pw.print(", ");
        }
        pw.print(")");
        pw.println();
	}

}


