//Laborat�rio de Compiladores - fase 2 
//Al�ssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

public class TypeVoid extends Type {
    
    public TypeVoid() {
        super("void");
    }
    
   public String getCname() {
      return "void";
   }

}