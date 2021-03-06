//Laboratório de Compiladores - fase 2 
//Aléssia Melo 		RA: 620289
//Leonardo Tozato 	RA: 620483

package ast;

abstract public class Type {

    public Type( String name ) {
        this.name = name;
    }

    public static Type booleanType = new TypeBoolean();
    public static Type intType = new TypeInt();
    public static Type stringType = new TypeString();
    public static Type voidType = new TypeVoid();
    public static Type undefinedType = new TypeUndefined();

    public String getName() {
        return name;
    }

    abstract public String getCname();

    private String name;

	public boolean isCompatible(Type targetType) {
		if(this == stringType && targetType == undefinedType){
			return true;
		}else if (this == voidType || targetType == voidType)
		{
			return false;
		}
		else if(this == booleanType || this == intType || this == stringType || targetType == booleanType || targetType == intType || targetType == stringType)
		{
			return this == targetType;
		}
		else if(targetType == undefinedType || this == undefinedType){
			return true;
		}
		else
		{
			return this == targetType || ((KraClass) targetType).isSubclassOf(this);
		}	
	}
}
