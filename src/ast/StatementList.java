//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class StatementList extends Statement {
	private ArrayList<Statement> stmtList;

	public StatementList() {
		stmtList = new ArrayList<Statement>();
	}

	public void add(Statement stmt) {
		stmtList.add(stmt);
	}

	public Iterator<Statement> elements() {
		return stmtList.iterator();
	}

	public int getSize() {
		return stmtList.size();
	}

	public void genKra(PW pw) {
<<<<<<< HEAD
		if (!stmtList.isEmpty()) {
			for (Statement s : stmtList) {
				if (s != null) {

					s.genKra(pw);
				}
			}
		}
=======
		if (!stmtList.isEmpty()) 
			for (Statement s : stmtList) 
				if (s != null) 							
					s.genKra(pw);		
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
	}

	@Override
	public void genCpp(PW pw) {
<<<<<<< HEAD
		if (!stmtList.isEmpty())
			for (Statement s : stmtList)
				if (s != null)
=======
		if (!stmtList.isEmpty()) 
			for (Statement s : stmtList) 
				if (s != null) 							
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
					s.genCpp(pw);
	}

}
