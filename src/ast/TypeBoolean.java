//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class TypeBoolean extends Type {

   public TypeBoolean() { 
	   super("boolean"); 
	}

   @Override
   public String getCname() {
	  
      return "int";
   }
   
   public String getName() {
	   return "boolean";
     
   }

}
