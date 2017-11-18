<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

package ast;

public class TypeBoolean extends Type {

   public TypeBoolean() { 
	   super("boolean"); 
	}

   /*@Override
   public String getCname() {
      return "int";
   }*/
   
   @Override
   public String getCname() {
	  
      return "bool";
   }
   
   public String getName() {
	   return "boolean";
     
   }

}
