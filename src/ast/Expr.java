//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483


package ast;

abstract public class Expr {
	
	abstract public void genCpp(PW pw, boolean putParenthesis);

	// new method: the type of the expression
	abstract public Type getType();

	abstract public void genKra(PW pw, boolean putParenthesis);
}