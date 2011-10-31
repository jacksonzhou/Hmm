package proj;

import static proj.AbstractSyntax.*;

import java.util.List;

public class Util 
{
    private static final String typeErrMsg = "Invalid Operator '{0}' for the types: {1} and {2}";
    
    public static Function findFunction(List<Function> functions, String name)
    {
        for (Function func: functions) {
            if (func.getName().equals(name)) {
                return func;
            }
        }
        return null;
    }

    /*
    *  added by MST
    *  finds a class in the program based on its name 
    */
    public static MyClass findClass(List<MyClass> classes, String name) 
    {
	for (MyClass c: classes) {
	    if (c.getName().equals(name)) {
		return c;
	    }
	} 
	return null;
    }
    
    
    public static void myAssert(boolean condition, String msg)
    {
    	if (condition == false) {
    		throw new UnsupportedOperationException(msg);
    	}
    }
  
    
    /**
     * @param v Must NEVER be null!
     */
    public static Value applyUnary (Unary un, Value v)
        throws InterpreterRuntimeError
    {
    	Operator op = un.getOp();
    	
        // Do the dynamic conversion to the correct types:
        if (op.isObject()) {
            Operator target = op.mapToType(v.getType());
            if (target == null) {
                throw new InterpreterRuntimeError(un.getLineNum(), typeErrMsg, op, v.getType(), "(NONE)" );
            }
            
            // Re-target the operator:
            op = target;
        }
        
        switch (op) {
        case INT_NEG:
            return new IntValue(-v.intValue());
        case FLOAT_NEG:
            return new FloatValue(-v.floatValue());
        case BOOL_NOT:
            return new BoolValue(!v.boolValue());
        default:
            throw new InterpreterRuntimeError(un.getLineNum(), typeErrMsg, op, v.getType(), "(NONE)" );
        }
    }

    
    
    /**
     * NOTE: The method expects the operation type be consistent
     * with argument types (should've been taken care of by the Static Check).
     * 
     * @param v1 First operand. Must NEVER be null (an unitialized check
     *           should've been done before).
     *           
     * @param v2 Second operand. Must NEVER be null.
     */
    public static Value applyBinary (Binary bin, Value v1, Value v2)
        throws InterpreterRuntimeError
    {
    	Operator op = bin.getOp();
    	
        // Do the dynamic conversion to the correct types:
        if (op.isObject()) {
            // Note, for most cases we are only expecting ints or floats:
            Type v1Type = v1.getType();
            Type v2Type = v2.getType();
            Operator target = null;
            
            if (v1.isNumber() && v2.isNumber()) {
                if (v1Type == BaseType.FLOAT || v2Type == BaseType.FLOAT) {
                    target = op.mapToType(BaseType.FLOAT);
                } else {
                    target = op.mapToType(BaseType.INT);
                }
            } else if (v1Type == v2Type && v1Type instanceof BaseType) {
                target = op.mapToType((BaseType)v1Type);
            }
            
            if (target == null) {
                throw new InterpreterRuntimeError(bin.getLineNum(), typeErrMsg, op, v1Type, v2Type);
            }
            // Re-target the operator:
            op = target;
        }
        
        switch (op) {
        case INT_LT:
            return new BoolValue(v1.intValue() < v2.intValue());
        case INT_LE:
            return new BoolValue(v1.intValue() <= v2.intValue());
        case INT_EQ:
            return new BoolValue(v1.intValue() == v2.intValue());
        case INT_NE:
            return new BoolValue(v1.intValue() != v2.intValue());
        case INT_GT:
            return new BoolValue(v1.intValue() > v2.intValue());
        case INT_GE:
            return new BoolValue(v1.intValue() >= v2.intValue());
        case INT_PLUS:
            return new IntValue(v1.intValue() + v2.intValue());
        case INT_MINUS:
            return new IntValue(v1.intValue() - v2.intValue());
        case INT_TIMES:
            return new IntValue(v1.intValue() * v2.intValue());
        case INT_DIV:
            return new IntValue(v1.intValue() / v2.intValue());
        case INT_MOD:
            return new IntValue(v1.intValue() % v2.intValue());
        // INT NEG must NOT be here!

        case FLOAT_LT:
            return new BoolValue(v1.floatValue() < v2.floatValue());
        case FLOAT_LE:
            return new BoolValue(v1.floatValue() <= v2.floatValue());
        case FLOAT_EQ:
            return new BoolValue(v1.floatValue() == v2.floatValue());
        case FLOAT_NE:
            return new BoolValue(v1.floatValue() != v2.floatValue());
        case FLOAT_GT:
            return new BoolValue(v1.floatValue() > v2.floatValue());
        case FLOAT_GE: 
            return new BoolValue(v1.floatValue() >= v2.floatValue());
        case FLOAT_PLUS:
            return new FloatValue(v1.floatValue() + v2.floatValue());
        case FLOAT_MINUS:
            return new FloatValue(v1.floatValue() - v2.floatValue());
        case FLOAT_TIMES:
            return new FloatValue(v1.floatValue() * v2.floatValue());
        case FLOAT_DIV:
            return new FloatValue(v1.floatValue() / v2.floatValue());
        case FLOAT_MOD:
            return new FloatValue(v1.floatValue() % v2.floatValue());
        // FLOATNEG is NOT here, because it's unary.

        // For Booleans, only == and != are defined.
        // But then, we also needs to support &&, ||:
        case BOOL_EQ:
            return new BoolValue(v1.boolValue() == v2.boolValue());
        case BOOL_NE: 
            return new BoolValue(v1.boolValue() != v2.boolValue());
        case BOOL_AND:
            return new BoolValue(v1.boolValue() & v2.boolValue());
        case BOOL_OR:
            return new BoolValue(v1.boolValue() | v2.boolValue());
        // Bool NOT is unary, so it's not here!
            
        // String operations:
        case STRING_LT:
            return new BoolValue(v1.stringValue().compareTo(v2.stringValue()) < 0);
        case STRING_LE:
            return new BoolValue(v1.stringValue().compareTo(v2.stringValue()) <= 0);
        case STRING_EQ: 
            return new BoolValue(v1.stringValue().compareTo(v2.stringValue()) == 0);
        case STRING_NE:
            return new BoolValue(v1.stringValue().compareTo(v2.stringValue()) != 0);
        case STRING_GT:
            return new BoolValue(v1.stringValue().compareTo(v2.stringValue()) > 0);
        case STRING_GE: 
            return new BoolValue(v1.stringValue().compareTo(v2.stringValue()) >= 0);
        case STRING_PLUS:
            return new StringValue(v1.stringValue() + v2.stringValue());

        // Type specific cast: disabled for now
            /*
        I2F("I2F"), 
        F2I("F2I");
        */
        default:
            throw new InterpreterRuntimeError(bin.getLineNum(), typeErrMsg, op, v1.getType(), v2.getType());
        }
    } 
}
