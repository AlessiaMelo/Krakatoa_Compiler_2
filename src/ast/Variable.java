<<<<<<< HEAD
//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
=======
//Alï¿½ssia Melo 		RA: 620289
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
//Leonardo Tozato 	RA: 620483

package ast;

public class Variable {

	private String name;
	private Type type;

	public Variable(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public void genKra(PW pw) {
		/// pw.println("varname");
		if (this.getType() == Type.booleanType || this.getType() == Type.stringType) {
			pw.printIdent(this.getType().getName());
		} else {
			pw.printIdent(this.getType().getCname());

		}
		pw.print(" ");
		pw.print(this.getName());
		pw.println(";");
	}

<<<<<<< HEAD
	public void genCpp(PW pw) {
=======
	public void genCpp(PW pw){
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
		pw.printIdent(this.getType().getCname());
		pw.print(" ");
		pw.print(this.getName());
		pw.println(";");
	}
<<<<<<< HEAD

=======
>>>>>>> ffe955430cc24857b879a8d4771fe1093d932992
}