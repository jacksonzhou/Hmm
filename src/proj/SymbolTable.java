// Modified by Melissa Olson, Sriratana Sutasirisap and Tanmaya Godbole (MST) Spring 2011

package proj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection; //added later 
import java.util.Set;

import proj.AbstractSyntax.*;
import proj.AbstractSyntax.Variable.VarType;
import static proj.Messages.*;
import static proj.Util.myAssert;

/**
 * 
 * @author miakhiae 
 */
public class SymbolTable {
	private final ErrLogger logger;
	private final Program prog;
	private Function curFunction;
	private Constructor curConstructor; //added later MST
	private MyClass curClass; //added later MST

	private int instanceCount = 0;
	
	private List<Function> functionsWithScopes = new ArrayList<Function>();
	
    private Map<String, Declaration> globalVariables = new HashMap<String, Declaration>();
    private Map<String, MyClass> globalClasses = new HashMap<String, MyClass>();
    private Map<String, Declaration> localVariables  = new HashMap<String, Declaration>();
    // a map of an object with a list of all its instance variables    
    private Map<MyObject, List<Declaration>> instanceVariables = new HashMap<MyObject, List<Declaration>>();
    private List<Scope> scopes = new ArrayList<Scope>();
    private List<LambdaContext> lambdaContexts = new ArrayList<LambdaContext>();

    
    private class Scope {
    	private int numScopeVars; //changed from "final" - MST
    	private List<String> scopeVarNames = new ArrayList<String>();
    	private final int baseCount;
	private int numClassVars = 0; //added later - MST
    	
    	public Scope(int baseCount, int count) {
    		this.baseCount = baseCount;
    		numScopeVars = count;
    	}
    	
    	public int getCurCount()
    	{
    		return baseCount + scopeVarNames.size();
    	}
    	
    	public void addVariable(Declaration decl)
    	{
    		String name = decl.getVariable().getName();
    		
    		if (localVariables.containsKey(name)) {
    			int prevLineNum = localVariables.get(name).getLineNum();
    			logger.error(decl.getLineNum(), REDECLARED_LOCAL, name, prevLineNum);
    			return;
    		}
    		
            myAssert(decl.getVariable().getVarType() == null, "The variable has already been declared!");
            
    		scopeVarNames.add(name);
    		myAssert(scopeVarNames.size() <= numScopeVars, 
    				"Incorrectly calculated Number of Scoped variables");
    		localVariables.put(name, decl);
    	}
        
    	
    	public void closeScope(int count)
    	{
    		myAssert(count == numScopeVars, "BUG: Invalid count is passed when closing the scope");	
    		// Remove all the local variables with the given name:
    		for (String name : scopeVarNames) {
    			localVariables.remove(name);
    		}
    	}
    }

    
    private class LambdaContext
    {
        private final LambdaDef lambda;
        private int addressCount;
        
        public LambdaContext(LambdaDef lambda)
        {
            this.lambda = lambda;
            // Assign addresses to the arguments:
            for (Variable var: lambda.getVars()) {
                // Make sure the Lambda variable hasn't been previously declared:
                Declaration oldDecl = findNormalDeclaration(var);
                
                if (oldDecl != null) {
                    logger.error(var.getLineNum(), REDECLARED_LOCAL,
                            var.getName(), oldDecl.getLineNum());
                }
                
                assignAddress(var, null);
            }
        }
        
        /**
         * If the given variable is a Lambda parameter,
         * copy parameter's address into it.
         * Otherwise, leave it alone and return false.
         */
        public boolean assignIfParameter(Variable var)
        {
            for (Variable lVar : lambda.getVars()) {
                if (lVar.getName().equals(var.getName())) {
                    var.setExecutionData(lVar);
                    return true;
                }
            }
            return false;
        }

        public LambdaDef getLambda() {
            return lambda;
        }

        public int getAddressCount() {
            return addressCount;
        }
        
        public void assignAddress(Variable var, Variable lambdaSource)
        {
            var.setExecutionData(Variable.VarType.LAMBDA, addressCount, lambdaSource);
            addressCount++;
        }
    }
    
    
    public SymbolTable(ErrLogger logger, Program prog)
    {
    	this.logger = logger;
    	this.prog = prog;
    	assignGlobals(prog.getGlobals());
    }
    
    private Declaration findNormalDeclaration(Variable var)
    {
        Declaration result = localVariables.get(var.getName());
    
        if (result == null) {
            result = globalVariables.get(var.getName());
        }
	myAssert(scopes.size() >= 1, "Declaration: Expected the number of currents scopes to be 1!"); //changed MST
        return result;
    }
    
    // added later - MST
   // This method checks if the variable is an instance variable of the object
   private Declaration findNormalOODeclaration(MyObject obj, Variable var)
    {
        List<Declaration> decList = instanceVariables.get(obj);
        Declaration result = null;
        for(Declaration decl : decList){
           String curName = decl.getVariable().getName();
           if(curName.equals(var.getName()))
             result = decl;
        }
	myAssert(scopes.size() >= 1, "Declaration: Expected the number of currents scopes to be 1!"); //changed MST
        return result;
    }

    /**
     * NOTE: The method does NOT flag an error if the variable is missing,
     * but it simply returns null.
     */
    public Type assignAddress(Variable var)
    {
        if (lambdaContexts.size() == 0) {
            // Assign the Local Variable:
            Declaration decl = findNormalDeclaration(var);
            if (decl == null) {
                return null;
            }
            // Ok, we've established the declaration: copy the variable type and address:
            var.setExecutionData(decl.getVariable());
            // Make sure the type is defined:
            myAssert(decl.getType() != null, "The type in the declaration is null");
            return decl.getType();
        } else {
            // Lambda Variable:
            LambdaContext ctx = lambdaContexts.get(lambdaContexts.size() - 1);
            if (ctx.assignIfParameter(var)) {
                return BaseType.OBJECT;
            } else {
                Declaration decl = findNormalDeclaration(var);
                if (decl == null) {
                    return null;
                }
                ctx.assignAddress(var, decl.getVariable());
                return decl.getType();
            }
        }
    }

   // added later - MST
  // This method finds the instance variable in the symbol table and  assigns an address to them
  public Type assignOOAddress(MyObject obj, Variable var)
    {
        if (lambdaContexts.size() == 0) {
            // Assign the Local Variable:
            Declaration decl = findNormalOODeclaration(obj, var);
            if (decl == null) {
                decl = findNormalDeclaration(var);
            }
            if (decl == null) {
            	return null;
            }
            //After we have established the declaration: copy the variable type and address:
            var.setExecutionData(decl.getVariable());     
            // Make sure the type is defined:
            myAssert(decl.getType() != null, "The type in the declaration is null");
            return decl.getType();
        } 
       return null;
  }
    // MST - changed scope to allow for inner scoping 
    public void startFunction(Function funct)
    {
    	curFunction = funct;
    	functionsWithScopes.add(funct);
    	// Ok, we'll ALWAYS add the function as a scope, even if it has no parameters:
    	scopes.add(new Scope(getCurCount(), funct.getNumScopeVariables()));
    	// The RETURN declaration always goes first!!!
    	addLocalDeclaration(funct.getReturnDecl()); //puts spot on stack for return value of current function
    	// Now, add the function parameters:
    	addLocalDeclarations(funct.getParams());
    }
    
    // MST - changed scope to allow for inner scoping
    public void endFunction()
    {
    	//myAssert(scopes.size() == 1, "endFunction: Expected the number of currents scopes to be 1!");
    	scopes.get(scopes.size() -1).closeScope(curFunction.getNumScopeVariables());
       //myAssert(localVariables.size() == 0, "Not all local variables have been removed for some reason!");
    	functionsWithScopes.remove(functionsWithScopes.size() - 1);
    	scopes.remove(scopes.size() -1);
    	if(scopes.size() > 0)
    		curFunction = functionsWithScopes.get(functionsWithScopes.size() - 1);
    	else
    		curFunction = null;
    }

    // added later by MST - this method opens a scope for the constructor when an object is instantiated
    public void startConstructor(Constructor cons)
    {
	//myAssert(curConstructor == null && scopes.size() == 0, "Constructor is already started!"); //changed because scope of constructor is contatined within scope of an object
	curConstructor = cons;
        scopes.add(new Scope(getCurCount(), cons.getNumScopeVariables()));
	addLocalDeclarations(cons.getParams());
    }

    // added later by MST - closes the scope of the constructor (method is called from staticTypeCheck)
    public void endConstructor(Constructor cons)
    {
	//myAssert(scopes.size() == 1, "endConstructor: Expected the number of currents scopes to be 1!"); //changed because scope of constructor is contatined within scope of an object
	scopes.get(scopes.size() - 1).closeScope(curConstructor.getNumScopeVariables());
    	//myAssert(localVariables.size() == 0, "Not all local variables have been removed for some reason!"); //constructor scope is inner scope of function in which instance is declared
	scopes.remove(scopes.size() - 1);
    	curConstructor = null;
    }

    
    public void startScope(Statement s)
    {
    	if (s.getNumScopeVariables() > 0) {
    		Scope scope = new Scope(getCurCount(), s.getNumScopeVariables());
    		scopes.add(scope);
    	}
    }
    
    public void startLambda(LambdaDef lambda)
    {
        // NOTE: The constructor will assign addresses 
        // to the Lambda-defined variables
        LambdaContext ctx = new LambdaContext(lambda);
        lambdaContexts.add(ctx);
    }
    
    /**
     * Check whether we are in the middle of processing a Lambda Definition:
     */
    public boolean isInsideLambda()
    {
        return !lambdaContexts.isEmpty();
    }
    
    public int endLambda(LambdaDef lambda)
    {
        LambdaContext ctx = lambdaContexts.remove(lambdaContexts.size() - 1);
        myAssert(ctx.getLambda() == lambda, "Invaliid startLambda/finishLambda finction call sequence");
        return ctx.getAddressCount();
    }
    
    public void addLocalDeclaration(Declaration decl)
    {
        myAssert(lambdaContexts.size() == 0, "Can not add any declarations when Lambdas are active!");
    	Scope lastScope = scopes.get(scopes.size() - 1);
        int curCount = lastScope.getCurCount();
        
    	lastScope.addVariable(decl);
    	decl.getVariable().setExecutionData(VarType.LOCAL, curCount + instanceCount, null); // added by MST
    }
    
    public void addLocalDeclarations(List<Declaration> decls) 
    {
    	for (Declaration decl : decls) {
    		addLocalDeclaration(decl);
    	}
    }

 
    // added later by MST - this method sets the type of the instance variables, and assigns them addresses on the stack. This information is recorded in the symbol table.
    public void createObj(MyObject obj) {
       Scope lastScope = scopes.get(scopes.size() - 1);
	int curCount = lastScope.getCurCount();
        MyClass c = globalClasses.get(obj.getType());
        List<Declaration> objVars = c.getGlobals();
        for(Declaration decl: objVars){
           Variable current = decl.getVariable();
           current.setExecutionData(VarType.INSTANCE, instanceCount + curCount, null);
           instanceCount++;
        }
        instanceVariables.put(obj, objVars);  
    }
    
    public void endScope(Statement s)
    {
    	if (s.getNumScopeVariables() > 0) {
    		Scope scope = scopes.remove(scopes.size() - 1);
    		scope.closeScope(s.getNumScopeVariables());
    	}
    }
    
    
    public Function findFunction(String name) {
        return Util.findFunction(prog.getFunctions(), name);
    }
    
    public Type getCurFunctionType()
    {
    	return curFunction.getReturnDecl().getType();
    }
    
    
    
    public int getCurCount()
    {
    	if (scopes.size() == 0) {
    		return 0;
    	}
    	Scope last = scopes.get(scopes.size() - 1);
    	return last.getCurCount();
    }
    
    private void assignGlobals(List<Declaration> globals)
    {
    	int globalCount = 0;
    	
    	for (Declaration global: globals) {
    		String name = global.getVariable().getName();
    		
    		if (globalVariables.containsKey(name)) {
    			logger.error(global.getLineNum(), REDECLARED_GLOBAL, name,
    					globalVariables.get(name).getLineNum());
    			continue;
    		}
    		
    		// Ok, no conflict at this point. We can put it into the map and assign address:
    		globalVariables.put(name, global);
    		global.getVariable().setExecutionData(Variable.VarType.GLOBAL, globalCount, null);
    		globalCount++;    		
    	}
    }
   // added later by MST - adds a class that has been defined to the symbol table
   public void addGlobalClass(MyClass myClass)
    {
	
	String name = myClass.getName();
		
	if(globalClasses.containsKey(name)) {
		logger.error(myClass.getLineNum(), REDECLARED_MYCLASS, name, 
				globalClasses.get(name).getLineNum());
	}
	
	globalClasses.put(name, myClass);
    }

    public boolean classExistence(String name){
        return globalClasses.containsKey(name);
    }

 
    public MyClass getClass(String className){
      MyClass c = globalClasses.get(className);
      return c;
    }
    
    public MyObject getObject(String name)
    {
       Set<MyObject> objects = instanceVariables.keySet();
       for(MyObject object: objects){
          if(object.getName().equals(name)){
            return object;
          }
    }
       return null;
    }
    
}
