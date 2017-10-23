//Alessia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

//atributes of a class
public class InstanceVariable extends Variable {
		public InstanceVariable( String name, Type type ) {
		super(name, type);
	}

	public void genKra(PW pw) {
		pw.printIdent("");
		pw.print("private ");
		//printing variables - extends
		pw.print(this.getType().getName());
		pw.print(" ");
		pw.print(this.getName());
		pw.println(";");
	}
	
	public void genCpp(PW pw) {
		//private not nedded
		pw.printIdent("");		
		pw.print(this.getType().getName());
		pw.print(" ");
		pw.print(this.getName());
		pw.println(";");
	}


}