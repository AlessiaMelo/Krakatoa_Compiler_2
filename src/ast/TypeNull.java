//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class TypeNull extends Type {

    public TypeNull() {
        super("null");
    }

    public String getCname() {
        return "null";
    }

}
