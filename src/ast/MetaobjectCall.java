//Laborat�rio de Compiladores - fase 1 
//Al�ssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

/**

 */
package ast;

import java.util.ArrayList;

/** This class represents a metaobject call as <code>{@literal @}ce(...)</code> in <br>
 * <code>
 * @ce(5, "'class' expected") <br>
 * clas Program <br>
 *     public void run() { } <br>
 * end <br>
 * </code>
 * 
   @author Jos�

 */
public class MetaobjectCall {

	private String name;
	private ArrayList<Object> paramList;

	public MetaobjectCall(String name, ArrayList<Object> paramList) {
		this.name = name;
		this.paramList = paramList;
	}

	public ArrayList<Object> getParamList() {
		return paramList;
	}
	public String getName() {
		return name;
	}



}
