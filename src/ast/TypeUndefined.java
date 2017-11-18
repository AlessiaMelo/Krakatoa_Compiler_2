//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class TypeUndefined extends Type {
    // variables that are not declared have this type
    
   public TypeUndefined() { 
	   super("undefined"); 
   }
   
   public String getCname() {
      return "int";
   }
   
}
