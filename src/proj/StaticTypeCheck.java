// StaticTypeCheck.java
// Static type checking for Clite, as in Chapter 6 and Appendix A3
package proj;

import static proj.AbstractSyntax.*;
import static proj.Messages.*;

import static proj.Util.myAssert;


import java.util.ArrayList;
import java.util.List;

import proj.AbstractSyntax.Assignment;
import proj.AbstractSyntax.BaseType;
import proj.AbstractSyntax.Block;
import proj.AbstractSyntax.Call;
import proj.AbstractSyntax.Conditional;
import proj.AbstractSyntax.DeclContainer;
import proj.AbstractSyntax.Expression;
import proj.AbstractSyntax.ForEach;
import proj.AbstractSyntax.LValue;
import proj.AbstractSyntax.ListTupleReference;
import proj.AbstractSyntax.Loop;
import proj.AbstractSyntax.Return;
import proj.AbstractSyntax.Value;
import proj.AbstractSyntax.Variable;


public class StaticTypeCheck 
{
    private ErrLogger logger = new ErrLogger();
    private SymbolTable symbolTable;

    
    void checkProgram(Program p) {
    	symbolTable = new SymbolTable(logger, p);
    	for (Function funct : p.getFunctions()) {
    		checkFunction(funct);
    	}
    }
    
    public ErrLogger getErrLogger()
    {
        return logger;
    }
    

    void checkFunction(Function f) {
    	symbolTable.startFunction(f);
    	checkStatement(f.getBody());
    	symbolTable.endFunction();
    }
    
    /**
     * Verify the expressions in the list. The resulting
     * types, however, will be discarded.
     */
    private void checkExpressions(List<Expression> expList)
    {
    	for (Expression exp : expList) {
    		checkExpression(exp);
    	}
    }
    
    public void checkStatement(Statement s)
    {
    	try {
    		symbolTable.startScope(s);
    	
	        if (s instanceof Assignment) {
	            Assignment ass = (Assignment) s;
	
	            Type expType = checkExpression(ass.getSource());
	            LValue lval = ass.getTarget();
	            
	            if (lval instanceof ListTupleReference) {
	                ListTupleReference ltRef = (ListTupleReference)lval;
	            	Type varType = processListRefGetVarType(ltRef);
	            	
	            	if (varType == BaseType.TUPLE) {
	            		logger.error(lval.getLineNum(), TUPLE_CANT_BE_LVAL, ltRef.getListVar().getName());
	            	}
	            } else if (lval instanceof Variable) {
	                Variable var = (Variable)lval;
	                
	                Type varType = processVariableUse(var);
	                testAssignment(expType, varType, lval.getLineNum(), -1);
	            } else {
	                throw new UnsupportedOperationException(
	                        "Unrecognized lvalue class: " + lval.getClass());
	            }
	            return;
	        }
	
	        if (s instanceof Conditional) {
	            Conditional cond = (Conditional) s;
	            Type testType = checkExpression(cond.getTest());
	            if ( typeMismatch(testType, BaseType.BOOL) ) {
	            	logger.error(cond.getTest().getLineNum(), IF_MUST_EVAL_TO_BOOL, testType);
	            }
	            
	            checkStatement(cond.getThenbranch());
	            if (cond.getElsebranch() != null) {
	            	checkStatement(cond.getElsebranch());
	            }
	            return;
	        }
	
	        if (s instanceof Loop) {
	            Loop loop = (Loop) s;
	            
	            Type testType = checkExpression(loop.getTest());
	            if ( typeMismatch(testType, BaseType.BOOL) ) {
	            	logger.error(loop.getTest().getLineNum(), WHILE_MUST_EVAL_TO_BOOL, testType);
	            }
	            
	            checkStatement(loop.getBody());
	            return;
	        }
	
	        if (s instanceof Return) {
	            Return ret = (Return) s;
	            Type funType = symbolTable.getCurFunctionType();
	            
	            // Make sure the 'void' type is acutally consistent
	            // with a return expression.
	            if (funType == BaseType.VOID && ret.getResult() != null) {
	            	logger.error(ret.getLineNum(), VOID_CAN_NOT_RETURN);
	            	return;
	            }
	            
	            if (funType != BaseType.VOID && ret.getResult() == null) {
	            	logger.error(ret.getLineNum(), NON_VOID_MUST_RETURN);
	            	return;
	            }
	            
	            // We also DO need to process the "variable" that serves as a return value:
	            checkExpression(ret.getTarget());
	            
	            if (ret.getResult() != null) {
	            	Type expType = checkExpression(ret.getResult());
	            	testAssignment(expType, funType, ret.getLineNum(), -1);
	            }
	            return;
 	        }
	        
	        if (s instanceof ConnectionToUrl) {
	        	return;
	        }
	        
	        if (s instanceof Insert) {
	        	return;
	        }
	        
	        if (s instanceof CreateDatabase) {
	        	return;
	        }
	        if (s instanceof DeclContainer) {
	        	List<Declaration> decList = ((DeclContainer)s).getDeclarations();
	        	
	        	for (Declaration decl : decList) {
	        		symbolTable.addLocalDeclaration(decl);
	        		if (decl.getInitValue() != null) {
	        			checkExpression(decl.getInitValue());
	        		}
	        	}
	        	return;
	        }

	        // NOTE: This will cover a case of the "Call"
	        if (s instanceof Expression) { 
	            checkExpression((Expression)s);
	            return;
	        }
	        
	        if (s instanceof ForEach) {
	            ForEach forEach = (ForEach)s;
                checkGeneratorList(forEach.getGenerator());
	            symbolTable.addLocalDeclarations(forEach.getGenerator().getDeclarations());
	            checkStatement(forEach.getBody());
	            return;
	        }
	
	        if (s instanceof Block) {
	        	// The variables are taken care of generically by the 
	        	// Symbol table, so all we need is to check all the statements:
	        	for (Statement subSt : ((Block)s).getMembers()) {
	        		checkStatement(subSt);
	        	}
	        	return;
	        }
	
	        /*
	        if (s instanceof Skip) {
	            return false;
	        }
	        */
    	}
    	finally {
    		symbolTable.endScope(s);
    	}

        throw new UnsupportedOperationException(
                "Unrecognized Statement class: " + s.getClass());
    }
    
    
    /**
     * This method only checks the generator list.
     * The variable must be checked by someone else!
     */
    private void checkGeneratorList(Generator gen)
    {
        Type genListType = checkExpression(gen.getList());
        
        if (typeMismatch(genListType, BaseType.LIST)) {
            logger.error(gen.getList().getLineNum(), 
                    GENERATOR_EXPECTING_LIST, genListType);
        }
    }

    
    /**
     * Does the static type check on the Expression and
     * finds out its type. 
     * 
     * @param exp
     * @return The type or 'null', if the variable hasn't been declared.
     */
    
    private Type checkExpression(Expression exp)
    {
    	try {
    		symbolTable.startScope(exp);
    	
	        if (exp instanceof Value) {
	            // There is nothing to check for the value.
	            // However, we can find out its type:
	            Value val = (Value)exp;
	            myAssert(val.getType() != null, "Value type is null");
	            return val.getType();
	        }
	
	        if (exp instanceof Variable) {
	        	return processVariableUse((Variable)exp);
	        }
	
	        if (exp instanceof Unary) {
	        	return processUnary((Unary)exp);
	        }
	
	        if (exp instanceof Binary) {
	        	return processBinary((Binary)exp);
	        }
	
	        if (exp instanceof ListTupleReference) {
	        	processListRefGetVarType((ListTupleReference)exp);
	            // We can't really say anything about the list-tuple 
	            // reference value other than it is an object.
	            return BaseType.OBJECT;
	        }
	
	        if (exp instanceof ListTupleExpression) {
	        	ListTupleExpression lte = (ListTupleExpression)exp;
	        	checkExpressions(lte.getMembers());
	        	return (lte.isTuple()) ? BaseType.TUPLE : BaseType.LIST;
	        }
	
	        if (exp instanceof ListComprehension) {            
	            ListComprehension listComp = (ListComprehension)exp;
	            
                // We are prohibiting List Comprehensions from appearing inside
	            // Lambda, because it messes up the whole Variable Allocation scheme
                if (symbolTable.isInsideLambda()) {
                    logger.error(listComp.getLineNum(), LIST_COMP_NOT_ALLOWED_IN_LAMBDA);
                    return BaseType.LIST;
                }
	            
	            // First, check all the genreator lists. They must NOT depend
	            // on the any variables inside the list comprehension:
	            for (Generator gen : listComp.getGenerators()) {
	                checkGeneratorList(gen);
	            }
	            
	            // Then, process all the declarations:
                for (Generator gen : listComp.getGenerators()) {
                    symbolTable.addLocalDeclarations(gen.getDeclarations());
                }
                
                // Now, verify the conditionals and the resulting expression:
                for (Expression cond : listComp.getConditionals()) {
                    Type condType = checkExpression(cond);
                    if (typeMismatch(condType, BaseType.BOOL)) {
                        logger.error(cond.getLineNum(), LIST_COMP_CONDITIONAL, condType);
                    }
                }
                
                checkExpression(listComp.getTarget());
                
                return BaseType.LIST;
	        }
	
	        if (exp instanceof LambdaDef) {
	            LambdaDef lambda = (LambdaDef)exp;
	            
	            if (symbolTable.isInsideLambda()) {
	                logger.error(lambda.getLineNum(), NESTED_LAMBDAS_NOT_ALLOWED);
	                // Though, nothing stops us from proceeding with this lambda.
	            }
	            
	            symbolTable.startLambda(lambda);
	            // The Lambda Definitions will processed by the 
	            // Symbol Table, so we just need to go through the expression:
	            checkExpression(lambda.getExpression());

	            // Once we're done, figure out the context size and finish the Lambda:
	            int contextSize = symbolTable.endLambda(lambda);
	            lambda.setContextSize(contextSize);
	            return lambda.getProtoType();
	        }
	
	        if (exp instanceof Call) {  
	            return processCall((Call)exp);
	        }
    	}
    	finally {
    		symbolTable.endScope(exp);
    	}
        
        throw new UnsupportedOperationException(
        		"Unrecognized Expression type: " + exp.getClass());
    }
    
    
    private Type processCall(Call call)
    {
        List<Expression> args    = call.getArgs();
        BuiltinFunctions.Name bfName = call.getBuiltinFunctName();
        
        if (bfName != null) {
            // The built-in functions are different!
            List<Type> typeList = new ArrayList<Type>();
            boolean success = true;
            
            for (int i = 0, size = args.size(); i < size; i++) {
                Type argType = checkExpression(args.get(i));
                if (argType == null) {
                    success = false;
                }
                typeList.add(argType);
            }
            
            if (success) {
                BuiltinFunctions.verifyArgs(logger, bfName, typeList);
            }
            return bfName.getReturnType();
        }
        
        // Just in case the call has been already processed, don't try to do it again!
        if (call.getFunction() != null) {
            return call.getFunction().getReturnDecl().getType();
        }
        
        FunctionType protoType;
        String name;
        
        if (call.getLambda() != null) {
            name = "<Lambda>";
            protoType = (FunctionType)checkExpression(call.getLambda());
        } else {
            if (call.getVar() == null) {
                throw new UnsupportedOperationException("Processing the call on line: " + call.getLineNum() + " more than once!");
            }
            name = call.getVar().getName();
        
            // First, we try to find local or global variable that represents a function:
            Type type = symbolTable.assignAddress(call.getVar());
            
            if ( type == null) {
                // Ok, if it's not a variable, try a function:
                Function funct = symbolTable.findFunction(name);
                if (funct == null) {
                    logger.error(call.getLineNum(), UNDEF_FUNCTION, name);
                    return null;
                }
                // Update the 'function' reference in the call:
                call.setFunction(funct, symbolTable.getCurCount());
                protoType = funct.getProtoType();
            } else if (type instanceof FunctionType) {
                protoType = (FunctionType)type;
            } else {
                logger.error(call.getLineNum(), INVALID_FUNC_REF, name);
                return null;
            }
        }

        int lineNum = call.getLineNum();
        List<Type> paramTypes = protoType.getParamTypes();
        
        // Checking the Prototype:
        if (call.getArgs().size() != paramTypes.size()) {
            logger.error(lineNum, INV_NUM_ARGS, name, paramTypes.size(), call.getArgs().size());
        } else {
            for (int i = 0, size = args.size(); i < size; i++) {
                Type argType = checkExpression(args.get(i));
                if (argType == null) {
                    continue;
                }
                testAssignment(argType, paramTypes.get(i), lineNum, i);
            }
        }
        
        return protoType.getResultType();
    }
    
    /**
     * The actual type must match expected, EXCEPT for the
     * expected type is allowed to be a 'null' (could not determine it),
     * or an OBJECT (checking is deferred to the runtime)
     */
    private boolean typeMismatch(Type actual, Type expected)
    {
    	return !(actual == expected || actual == BaseType.OBJECT || actual == null);
    }
    
    /**
     * Check whether we can assign from a value of one type
     * to a value of the other type. Give out an error
     * message if we could not do this. 
     * 
     * @param from
     * @param to
     * @param lineNum
     * 
     * @param argNum  Argument number in the function or (-1) for the assignment:
     * 
     * @return true/false depending on whether the check passed or failed. 
     */
    private boolean testAssignment(Type from, Type to, int lineNum, int argNum)
    {
    	// If any of the types is invalid, we can't do anything else.
    	// But, we shouldn't print an error message, because it's already being printed!
    	if (to == null || from == null) {
    		return false;
    	}
    	
    	if (from.canBeAssignedTo(to)) {
    		return true;
    	}
    	
    	// If nothing works, this is an error.
    	if (argNum >= 0) {
    		logger.error(lineNum, PROTO_MISMATCH, argNum, to, from);
    	} else {
    		logger.error(lineNum, INV_ASSIGNMENT, from, to);
    	}
    	
    	return false;
    }
    

    /**
     * WARNING: Unlike most of the other methods, this one 
     * returns the type of the LIST VARIABLE, not the expression
     * (which would be OBJECT anyways).
     */
    private Type processListRefGetVarType(ListTupleReference ref)
    {        
        // NOTE, we don't have to return right away, even if
        // the index type is invalid:
        Type indexType = checkExpression(ref.getIndex());
        if ( typeMismatch(indexType,  BaseType.INT) ) {
        	logger.error(ref.getLineNum(), INDEX_MUST_BE_INTEGER, indexType, BaseType.INT);
        }
       
        Type type = processVariableUse(ref.getListVar());
        if (type != BaseType.LIST && type != BaseType.TUPLE && type != null) {
        	logger.error(ref.getLineNum(), WRONG_LIST_TYPE, ref.getListVar().getName(), type);
        	return null;
        } else {
        	return type;
        }
    }
    
    private Type processUnary(Unary un)
    {
        Type termType = checkExpression(un.getTerm());
        boolean boolResult = un.getOp().isBoolResult();
        if (termType == null) {
        	// Error case: skip all subsequent checking.
        	return null;
        }
        // If we happened to hit an objec, the operation
        // Resolution will be deferred to the Runtime.
        if (termType == BaseType.OBJECT) {
        	return boolResult ? BaseType.BOOL : BaseType.OBJECT;
        }
        
        Operator newOp = un.getOp().mapToType(termType);
        if (newOp == null) {
        	logger.error(un.getLineNum(), UNDEF_UNARY_FOR_TYPE, un.getOp(), termType);
        	return null;
        }
        // Update the operation in the unary and be done with it:
        un.setOp(newOp);
        // The operation shall NOT change the type, so the result
        // type is exactly the same as the term type:
        return boolResult ? BaseType.BOOL : termType;
    }
    
    
    private Type processBinary(Binary bin)
    {
        Type type1 = checkExpression(bin.getTerm1());
        Type type2 = checkExpression(bin.getTerm2());
        boolean boolResult = bin.getOp().isBoolResult();
                
        if (type1 == null || type2 == null) {
        	// Error case: skip all subsequent checking.
        	return null;
        }
        
        // If both of types are objects, the result is 
        // an object with no argument.
        if (type1 == BaseType.OBJECT && type2 == BaseType.OBJECT) {
        	return (boolResult) ? BaseType.BOOL : BaseType.OBJECT;
        }
        
        // Now, find out the resulting operation type:
        Type opType = null;
        Operator newOp;
        
        if (type1 == type2) {
        	// If the types are equal (and they are not BOTH OBJECTS, as 
        	// verified above), then the operator type will be one of these:
        	opType = type1;
        } else if (isFloatIntObj(type1) && isFloatIntObj(type2)) {
        	// We only bother about conversion when the operands
        	// are of FLOAT, INT, or OBJECT type.
        	//
        	// Now, if any of them is a FLOAT, then the operator shall be a FLOAT:
        	if (type1 == BaseType.FLOAT || type2 == BaseType.FLOAT) {
        		opType = BaseType.FLOAT;
        	} else {
        		// So now we know that:
        		//  - Both types are in the set:  INT, FLOAT, OBJECT.
        		//  - In fact, they are in INT, OBJECT, because as stated
        		//    above neither of them is float.
        		//  - Their values are not equal.
        		//
        		//  Therefore, either of these 2 cases:
        		//   - type1=INT, type2=OBJECT.
        		//   - type1=OBJECT, type2=INT
        		//
        		//  Now, we can't do anything statically, because the OBJECT
        		//  may appear to be FLOAT. So let's resort to the runtime checking.
        		return BaseType.OBJECT;
        	}
        } else if (type1 == BaseType.OBJECT) {
            opType = type2;            
        } else if (type2 == BaseType.OBJECT) {
            opType = type1;
        }
        
        // The error happens if we failed to establish the
        // 'opType' or the 'opType' does not map the operator anywhere.
        // NOTE: It is valid NOT to map the operator anywhere if the
        // operation is OBJECT, OBJECT, but we've just considered
        // such case above.
        if ( opType == null || 
            (newOp = bin.getOp().mapToType(opType)) == null) {
    		logger.error(bin.getLineNum(), UNDEF_BINARY_FOR_TYPES, bin.getOp(), type1, type2);
    		return null;
        }
        
        // Ok, we found out the New Op. Update and live happy ever after!
        bin.setOp(newOp);
        
        return (boolResult) ? BaseType.BOOL : opType;
    }
    
    private boolean isFloatIntObj(Type t)
    {
    	return t == BaseType.FLOAT || t == BaseType.INT || t == BaseType.OBJECT;
    }


    /**
     * The method must do the following:
     * <ul>
     * <li> Find out whether it is global, local, or Lambda variable. </li>
     * <li> Copy its execution data </li>
     * <li> Return its type </li>
     *
     * @return The type or null, if the variable was NOT declared.
     * 
     * @throws AbortedException If the variable was NOT declared.
     */
    private Type processVariableUse(Variable var)
    {
        // First, check whether it is a local:
        Type type = symbolTable.assignAddress(var);
        
        if (type == null) {
            logger.error(var.getLineNum(), VAR_UNDEFINED, var.getName());
            return null;
        }
        return type;
    }
} // class StaticTypeCheck

