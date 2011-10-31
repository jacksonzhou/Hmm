package proj;

public class Messages {
    public static final String 
    
    	REDECLARED_GLOBAL = "The Global Variable \"{0}\" has already been declared on line {1}.",
    	
    	REDECLARED_LOCAL  = "The Local Variable \"{0}\" has already been declared on line {1}.",
    
        UNDEFINED_CLASS = "This object cannot be instantiated because this class cannot defined", //added by MST
        USED_NOT_INIT = "Value of \"{0}\" is used without being initialized",
        INDEX_OUT_OF_BOUNDS = "Index out-of-bounds: \"{0}\"; the size of list \"{1}\" is {2}",
        WRONG_LIST_TYPE = "Variable \"{0}\" was expected to be a list or tuple, but it is \"{1}\"",
        INDEX_MUST_BE_INTEGER = "List index is of type: \"{0}\", while it must be: \"{1}\"",
        IF_MUST_EVAL_TO_BOOL = "The check in the \"if\" evaluated to the type \"{0}\", while it should have been BOOL",
        WHILE_MUST_EVAL_TO_BOOL = "The test in the \"while\" evaluated to the type \"{0}\", while it must have been BOOL",
        
        TUPLE_CANT_BE_LVAL = "Invalid assignment to \"{0}[...]\". The tuple elements can NOT be assigned values.",

        MAIN_NOT_FOUND     = "Can not find the \"main\" function in the program",
        
        VAR_UNDEFINED      = "Undefined variable: \"{0}\"",
        
    	UNDEF_UNARY_FOR_TYPE  = "The unary operation \"{0}\" is NOT defined for the type \"{1}\"",
    	UNDEF_BINARY_FOR_TYPES = "The binary operation \"{0}\" is NOT defined for the argument types: \"{1}\" and \"{2}\"",
    
    	UNDEF_FUNCTION         = "Undefined function: \"{0}\"",
    	
    	INV_ASSIGNMENT         = "Can not assign the value of type \"{0}\" to a value of type \"{1}\"",
    	PROTO_MISMATCH         = "The type of argument {0} in the function call does not match the prototype: expecting {1}, but got {2}",
    	INV_NUM_ARGS           = "Invalid number of arguments in the call of \"{0}\": expecting {1}, but got {2}",
    
    	NON_VOID_MUST_RETURN   = "The 'return' statement must specify a value, because the function is NOT 'void'",
    	VOID_CAN_NOT_RETURN    = "The 'return' statement can NOT specify a value, because the function is 'void'",
    
        FUNCTION_DID_NOT_RETURN_VALUE = "The function \"{0}\" did execute the return statement",
        
        GENERATOR_EXPECTING_LIST = "Expecting a LIST after <-, but got \"{0}\"",
    
        INV_GENERATOR_VARS       = "The expression preceding \"<-\" must be either a single variable or a tuple of variables",
        
        GENERATOR_EXP_LIST_TUPLE = "The values of the Generator list are themselves expected to be lists or tuples, but we encountered: \"{1}\"",
        GENERATOR_INV_NUM        = "The sub-tuples (or sub-lists) in the list are expected to contain {0} elements, but they in fact contain {1}.",
        
        LIST_COMP_CONDITIONAL    = "The conditional expression in the List Comprehension must be BOOL, but it was \"{0}\"",
    
        INVALID_FUNC_REF         = "The variable \"{0}\" is not of a valid function type",
        
        LIST_COMP_NOT_ALLOWED_IN_LAMBDA = "The List Comprehension is NOT ALLOWED inside Lambda Expressions",
        NESTED_LAMBDAS_NOT_ALLOWED = "The nested Lamba expressions are NOT ALLOWED",
    
        EXPECTING_LAMBDA         = "Expecting a value of the Lambda type, but got: \"{0}\"",

	REDECLARED_MYCLASS = "Class already declared"; //added by MST

}
