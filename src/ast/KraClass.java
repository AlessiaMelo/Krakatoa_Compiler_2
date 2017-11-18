//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483


package ast;

import java.util.ArrayList;
import java.util.Iterator;

import lexer.Symbol;

/*
 * Krakatoa Class
 */
public class KraClass extends Type {
   private String name;
   private KraClass superclass;
   private InstanceVariableList instanceVariableList;
   private ArrayList<Method> publicMethodList, privateMethodList;
	
   public KraClass( String name ) {
      super(name);
      instanceVariableList = new InstanceVariableList();
      publicMethodList = new ArrayList<>();
      privateMethodList = new ArrayList<>();
   }
   
   public String getCname() {
      return getName();
   }

   public void setSuper(KraClass superClass){
       superclass = superClass;
   }
   
  public boolean isSubclassOf(Type returnType) {
		KraClass current = this;
		while(current != returnType){
			current = current.getSuperclass();
			if(current == null)
				return false;
		}
		return true;
	}

	public KraClass getSuperclass() {
		return superclass;
	}

	public Method searchPublicMethod(String id) {
		for(Method m : publicMethodList)
			if(m.getName().equals(id))
				return m;
		return null;			
	}
	
	public Method searchPrivateMethod(String id) {
		for(Method m : privateMethodList)
			if(m.getName().equals(id))
				return m;
		return null;			
	}

	public void addMethod(Method currentMethod, Symbol qualifier) {
		if(qualifier == Symbol.PRIVATE)
			privateMethodList.add(currentMethod);
		else
			publicMethodList.add(currentMethod);
		
	}

	public void addVariable(InstanceVariable v) {
		instanceVariableList.addElement(v);
	}

	public Method searchMethod(String id) {
		for(Method m : publicMethodList)
			if(m.getName().equals(id))
				return m;
		for(Method m : privateMethodList)
			if(m.getName().equals(id))
				return m;
		return null;
	}

	public InstanceVariable getVar(String name) {
		Iterator<InstanceVariable> it = instanceVariableList.elements();
		while(it.hasNext()){
			InstanceVariable v = it.next();
			if(v.getName().equals(name))
				return v;
		}
		return null;
	}


	public void genKra(PW pw) {
		
		pw.println();
		pw.printIdent("class " + this.getCname());
		if(superclass != null)
			pw.print(" extends " + superclass.getName());
		pw.print(" {");
		pw.println("");
		pw.add();
		if(instanceVariableList != null)
			instanceVariableList.genKra(pw);
		if(privateMethodList != null)
			for(Method m : privateMethodList)
				m.genKra(pw);
		if(publicMethodList != null)
			for(Method m : publicMethodList)
				m.genKra(pw);
		pw.sub();
		pw.printIdent("}");
		pw.println("");
		
	}
	
public void genCpp(PW pw) {
		
		pw.println();
		pw.printIdent("class " + this.getCname());
		if(superclass != null)
		{
			//check if it's really private
			pw.print(" : private " + superclass.getName());
		}
		pw.print(" {");
		pw.println("");
		pw.add();
		if(instanceVariableList != null)
			instanceVariableList.genCpp(pw);
		if(privateMethodList != null)
		{
			pw.println("private: ");
			for(Method m : privateMethodList)
			{
				m.genCpp(pw);
			}
		}
		if(publicMethodList != null)
		{
			pw.println("public: ");
			for(Method m : publicMethodList)
			{
				m.genCpp(pw);
			}
		}
		pw.sub();
		pw.printIdent("};");
		pw.println("");
		
	}

}


/*

//classes example
#include <iostream>
using namespace std;

class Rectangle {
 int width, height;
public:
 void set_values (int,int);
 int area() {return width*height;}
};

void Rectangle::set_values (int x, int y) {
width = x;
height = y;
}

int main () {
Rectangle rect;
rect.set_values (3,4);
cout << "area: " << rect.area();
return 0;
}



https://pt.wikibooks.org/wiki/Programar_em_C%2B%2B/Herança

// constructors and derived classes
#include <iostream>
using namespace std;

class Mother {
  public:
    Mother ()
      { cout << "Mother: no parameters\n"; }
    Mother (int a)
      { cout << "Mother: int parameter\n"; }
};

class Daughter : public Mother {
  public:
    Daughter (int a)
      { cout << "Daughter: int parameter\n\n"; }
};

class Son : public Mother {
  public:
    Son (int a) : Mother (a)
      { cout << "Son: int parameter\n\n"; }
};

int main () {
  Daughter kelly(0);
  Son bud(0);
  
  return 0;
}

*/

}