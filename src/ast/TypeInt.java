//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483


package ast;

public class TypeInt extends Type {
    
    public TypeInt() {
        super("int");
    }
    
   public String getCname() {
      return "int";
   }

}