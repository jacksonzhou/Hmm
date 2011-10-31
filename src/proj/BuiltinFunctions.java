package proj;

import static proj.AbstractSyntax.*;

import java.util.List;

public class BuiltinFunctions {
   
    public enum Name {
        print(BaseType.VOID),
        println(BaseType.VOID);
        
        private BaseType returnType;
        
        private Name(BaseType returnType) {
            this.returnType = returnType;
        }
        
        public BaseType getReturnType()
        {
            return returnType;
        }
    }

    private static final String NO_DELIM   = "";
    private static final String BASE_DELIM = "    ";
    
    public static Value run(Name name, List<Value> args)
    {
        switch (name) {
        case print:
            print(args);
            return null;
        case println:
            println(args);
            return null;
        default:
            throw new UnsupportedOperationException(
                    "Unrecognized builtin function: " + name);
        }
    }
    
    public static boolean verifyArgs(ErrLogger logger, Name name, List<Type> types)
    {
        switch (name) {
        case print:
        case println:
            return true;
        default:
            throw new UnsupportedOperationException(
                    "Unrecognized Builtin function: " + name);
        }
    }
    

    public static void println(List<Value> args)
    {
        print(args);
        System.out.println();
    }
    
    public static void print(List<Value> args) 
    {
        for (Value val : args) {
            append(NO_DELIM, val);
        }
    }

    private static void append(String curDelim, Value val) {
        Type type = val.getType();

        if (type instanceof BaseType == false) {
            System.out.print("<Lambda ?>");
            return;
        }
        
        switch ((BaseType) type) {
        case INT:
            System.out.print(val.intValue());
            break;
        case BOOL:
            System.out.print(val.boolValue());
            break;
        case FLOAT:
            System.out.print(val.floatValue());
            break;
        case STRING:
            System.out.print(val.stringValue());
            break;
        case TUPLE:
        case LIST:
            System.out.print((type == BaseType.LIST) ? "[": "(");
            
            String delim = null;
            if (hasNestedLists(val.subListValue())) {
                delim = curDelim + BASE_DELIM;
                System.out.println();
            }            
            for (int i = 0, size = val.subListValue().size(); i < size; i++) {
                if (delim != null) {
                    System.out.print(delim);
                }
                append(delim, val.subListValue().get(i));
                if (i < size - 1) {
                    System.out.print(",");
                    if (delim != null) {
                        System.out.println();
                    } else {
                        System.out.print(" ");
                    }
                }
            }
            if (delim != null) {
                System.out.println(curDelim);
            }
            System.out.print((type == BaseType.LIST) ? "]": ")");
            break;

        default:
            System.out.print("<???>");
        }
    }

    private static boolean hasNestedLists(List<Value> vals) {
        for (Value val : vals) {
            Type type = val.getType();
            if (type == BaseType.LIST || type == BaseType.TUPLE) {
                return true;
            }
        }
        return false;
    }
}
