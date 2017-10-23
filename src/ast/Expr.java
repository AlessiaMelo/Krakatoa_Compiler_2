//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

abstract public class Expr {	
	// new method: the type of the expression
	abstract public Type getType();

	abstract public void genKra(PW pw, boolean putParenthesis);
	
	abstract public void genCpp(PW pw, boolean putParenthesis);

}