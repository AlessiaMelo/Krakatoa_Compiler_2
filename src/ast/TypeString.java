//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class TypeString extends Type {
    
    public TypeString() {
        super("String");
    }
    
   public String getCname() {
      return "char *";
   }
   
   public String getName() {
	      return "String";
	   }

}