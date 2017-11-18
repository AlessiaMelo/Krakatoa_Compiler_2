<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

package ast;

public class TypeString extends Type {
<<<<<<< HEAD

	public TypeString() {
		super("String");
	}

	/*
	 * public String getCname() { return "char *"; }
	 */

	public String getCname() {
		return "string";
	}

	public String getName() {
		return "String";
	}
=======
    
    public TypeString() {
        super("String");
    }
    
   public String getCname() {
      return "string";
   }
   
   public String getName() {
	      return "String";
	   }
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992

}