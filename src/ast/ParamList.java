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
	        for ( Parameter p : paramList ) {
	            p.genKra(pw);
	            if ( --size > 0 )
	                pw.print(", ");
	        }
	}
}