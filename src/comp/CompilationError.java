//Laboratório de Compiladores - fase 1 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package comp;

/**
 * a compilation error
 * 
   @author José
 */
public class CompilationError {


	public CompilationError(String message, int lineNumber, String lineWithError) {
		this.message = message;
		this.lineNumber = lineNumber;
		this.lineWithError = lineWithError;
	}
	
	private static final long	serialVersionUID	= 1L;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getLineWithError() {
		return lineWithError;
	}
	public void setLineWithError(String lineWithError) {
		this.lineWithError = lineWithError;
	}

	private String	message;
	private int	lineNumber;
	private String	lineWithError;

}
