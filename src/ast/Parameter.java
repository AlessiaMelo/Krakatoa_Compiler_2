//Al�ssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class Parameter extends Variable {

    public Parameter(String name, Type type ) {
        super(name, type);
    }

    public void genKra(PW pw){
        pw.print(this.getType().getName() + " " + this.getName());
    }

}