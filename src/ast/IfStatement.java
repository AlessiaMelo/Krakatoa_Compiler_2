//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

//add extends
public class IfStatement extends Statement {
	//IfStat ::= "if" "(" Expression")" Statement ["else" Statement ]

	private Expr expr;
	private Statement ifPart, elsePart;

	public IfStatement(Expr e, Statement ifs, Statement elses) {
		this.expr = e;
		this.ifPart = ifs;
		this.elsePart = elses;
	}

	public void genKra(PW pw){
		pw.printIdent("if( ");
		this.expr.genKra(pw, false);
		pw.println(") ");
		this.ifPart.genKra(pw);

		if(this.elsePart != null){
			pw.add();
			pw.printIdent("else ");
			this.elsePart.genKra(pw);
			pw.sub();
		}
	}

	@Override
	public void genCpp(PW pw) {
		pw.printIdent("if( ");
		this.expr.genCpp(pw, false);
		pw.print(") ");
		this.ifPart.genCpp(pw);
		if (this.elsePart != null) {
			pw.printIdent("else ");
			this.elsePart.genCpp(pw);
		}

	}
}
