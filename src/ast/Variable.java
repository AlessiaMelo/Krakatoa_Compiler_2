//Aléssia Melo 		RA: 620289
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
		///pw.println("varname");
		if(this.getType() == Type.booleanType || this.getType() == Type.stringType )
		{
			pw.printIdent(this.getType().getName());
		}		
		else
		{
			pw.printIdent(this.getType().getCname());
			
		}
		pw.print(" ");
		pw.print(this.getName());
		pw.println(";");
	}

}