package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class StatementList extends Statement{
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
		if (!stmtList.isEmpty()) {
			for (Statement s : stmtList) {
				if (s != null) {
									
					s.genKra(pw);
				}
			}
		}
	}

	@Override
	public void genC(PW pw) {
		// TODO Auto-generated method stub
		
	}

}
