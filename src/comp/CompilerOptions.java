//Laborat�rio de Compiladores - fase 1 
//Al�ssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package comp;

public class CompilerOptions {
    
   public CompilerOptions() {
      count = false;
      outputInterface = false;
      extractClass = false;
   }
   
   public void setCount(boolean count) {
      this.count = count;
   }
   public boolean getCount() {
      return count;
   }
   
   public void setOutputInterface( boolean outputInterface ) {
      this.outputInterface = outputInterface;
   }
   
   public boolean getOutputInterface() {
      return outputInterface;
   }
   
   public void setExtractClass( boolean extractClass ) {
      this.extractClass = extractClass;
   }
   
   public boolean getExtractClass() {
      return extractClass;
   }
   
   private boolean count, outputInterface, extractClass;
   
}