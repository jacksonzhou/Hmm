// Abstract syntax for Clite, as in Chapter 2 and Appendix A2
// Modified by Melissa Olson, Sriratana Sutasirisap and Tanmaya Godbole (MST) Spring 2011

package proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

import static proj.Messages.*;

import proj.parser.ParseException;
import proj.parser.Token;
import proj.AbstractSyntax.Statement;


/**
 * Abstract Syntax is a container class for for all the sub-classes.
 * 
 * @author miakhiae
 */
public class AbstractSyntax {

    public static class Indenter {
        public int level;

        public Indenter(int nextlevel) {
            level = nextlevel;
        }

        public void display(String message) {
            // displays a message on the next line at the current level
            String tab = "";
            Debug.println();
            for (int i = 0; i < level; i++)
                tab = tab + "  ";
            System.out.print(tab + message);
        }
    } // Indenter
    
    public static interface LValue
    {
        void display(int level);
        int getLineNum();
    }
    
    public static String returnName(String funcName)
    {
        return "return#" + funcName;
    }
    
    /**
     * There is a problem enforcing the variable-tuple rule in the grammar, 
     * so we'll have to enforce it via a separate function.
     * 
     * @param vars
     * @param value
     * @return
     */
    public static Generator constructGenerator(Expression vars, Expression value)
        throws ParseException
    {
        if (vars instanceof Variable) {
            return new VariableGenerator((Variable)vars, value);
            
        } 
        
        if (vars instanceof ListTupleExpression == false) {
            throw new ParseException(fmtParseErrMsg(
                    vars.getLineNum(), INV_GENERATOR_VARS));
        }
        
        List<Variable> result = new ArrayList<Variable>();
        ListTupleExpression listExp = (ListTupleExpression)vars;
        if (listExp.isTuple() == false) {
            throw new ParseException(fmtParseErrMsg(
                    vars.getLineNum(), INV_GENERATOR_VARS));
        }
        
        for (Expression exp: listExp.getMembers()) {
            if (exp instanceof Variable == false) {
                throw new ParseException(fmtParseErrMsg(
                        vars.getLineNum(), INV_GENERATOR_VARS));
            }
            
            Variable var = (Variable)exp;
            if ("_".equals(var.getName())) {
                result.add(null);
            } else {
                result.add(var);
            }
        }
        
        return new TupleGenerator(result, value);
    }
    
    public static String fmtParseErrMsg(int lineNum, String msg, Object ... args)
    {
        String fmt = MessageFormat.format(msg, args);
        return "ERROR, line " + lineNum + ": " + fmt;
    }
    
    
    public static void displayClass(Object obj, int level)
    {
        Indenter indent = new Indenter(level);
        indent.display(obj.getClass().getSimpleName() + ": ");
    }
    
    public static void displayMsg(String msg, int level)
    {
        Indenter indent = new Indenter(level);
        indent.display(msg);
    }

    public static class Program {
        private List<Declaration> globals;
        private List<Function> functions;
        private List<MyClass> classes;
        private List<MyObject> objects;
        public Program(List<Declaration> globals, List<Function> functions, List<MyClass> classes, List<MyObject> objects)
        {
            this.globals = globals;
            this.functions = functions;
	    this.classes = classes;
            this.objects = objects;
        }

        public void display() {
            int level = 0;
            Indenter indent = new Indenter(level);
            indent.display("Program (abstract syntax): ");
            for (Declaration decl : globals) {
                decl.display(level + 1);
            }
            for (Function func : functions) {
                func.display(level + 1);
            }
            for (MyClass myClass : classes) {
                myClass.display(level + 1);
            }
            for (MyObject myObj : objects) {
                myObj.display(level + 1);
            }

            Debug.println();
        }
        
        public int getNumGlobals()
        {
            return globals.size();
        }

        public List<Declaration> getGlobals() {
            return globals;
        }

        public List<Function> getFunctions() {
            return functions;
        }

  	public List<MyClass> getClasses() {
            return classes;
        }
        
    }

    // added later by MST
    public static class Constructor {
        private String name;
        private int lineNum;
        private List<Declaration> params;
        private Block body;

        public Constructor(Token token, List<Declaration> decls, Block body) {
	    this.name = token.image;
            this.lineNum = token.beginLine;
            this.params = decls;
            this.body = body;
        }
        
        public int getNumScopeVariables()
        {
        	// Note, we include ONLY the parameters. The local
        	// variables are declared within the scope of
        	// the corresponding blocks:
        	return params.size();
        }

        public void display(int level) {
            Indenter indent = new Indenter(level);
            indent.display("Constructor = " + name);
            indent.display("  params = ");
            for (Declaration decl : params) {
                decl.display(level + 1);
            }
            body.display(level + 1);
        }
        
        public int getLineNum()
        {
        	return lineNum;
        }
        

        public String getName() {
            return name;
        }

        public List<Declaration> getParams() {
            return params;
        }

        public Block getBody() {
            return body;
        }

    }

    // added later by MST 
    public static class MyObject extends Statement {
       private String objName;
       private String className;
       private List<Expression> args;
       private int lineNum;

       public MyObject(Token objName, Token className,  List<Expression> args) {
           this.objName = objName.image;
           this.lineNum = objName.beginLine;
           this.className = className.image;
           this.args = args;
       }

       public void display(int level) {
            Indenter indent = new Indenter(level);
            indent.display("Object = " + objName + "; Object type = " + className);
            indent.display("  args = ");
            for (Expression exp : args) {
                exp.display(level + 1);
            }
        }
        public int getLineNum()
        {
        	return lineNum;
        }
       
        public String getName()
        { 
            return objName;
        }
    
        public String getType()
        {
           return className;
        }
   
        public List<Expression> getArgs() {
            return args;
        }

    }

    // added later by MST
    public static class ObjFunction extends Expression{
       private String objName;
       private String className;
       private String funName;
       private List<Expression> args;
       private int lineNum;
       private int stackOffset;
       private Function function;
       
       public ObjFunction(Token className, Token objName, Token funName, List<Expression> args){
         this.className = className.image;
         this.objName = objName.image;
         this.funName = funName.image;
         lineNum = funName.beginLine;
         this.args = args;
       }   
       
       public String getObjName(){
         return objName;
       }
       
       public String getFuncName(){
          return funName;
       }
       
       public List<Expression> getArgs(){
          return args;
       }
        
       public int getLineNum(){
          return lineNum;
       }
       
       public String getClassName(){
          return className;
       }
       
       public void display(int level) {
            Indenter indent = new Indenter(level);
            indent.display("Object Function = " + objName + "; Function Name = " + funName);
            indent.display("  args = ");
            for (Expression exp : args) {
                exp.display(level + 1);
            }
        }
        
        public void setOOFunction(Function funct, int stackOffset)
        {
        	this.function = funct;
        	this.stackOffset = stackOffset;
        }
        public void setStackOffset(int stackOffset){ 
		this.stackOffset = stackOffset;
	}
	
	public int getStackOffset(){ 
              return stackOffset;
	}

    
    }
    
    public static class Function {
        // Function = Type t; String id; Declarations params;
        // Declarations locals; Block body
        private Declaration returnDecl;
        private String name;
        private int lineNum;
        private List<Declaration> params;
        private Block body;

        public Function(Type returnType, Token token, List<Declaration> decls, Block body) {
            this.name = token.image;
            this.lineNum = token.beginLine;
            this.params = decls;
            this.body = body;

            this.returnDecl = new Declaration(returnType, returnName(this.name), lineNum);
        }
        
        public int getNumScopeVariables()
        {
        	// Note, we include ONLY the parameters. The local
        	// variables are declared within the scope of
        	// the corresponding blocks:
        	return params.size() + (returnDecl.getType() == BaseType.VOID ? 0 : 1);
        }

        public void display(int level) {
            Indenter indent = new Indenter(level);
            indent.display("Function = " + name + "; Return type = " + returnDecl.getType());
            indent.display("  params = ");
            for (Declaration decl : params) {
                decl.display(level + 1);
            }
            body.display(level + 1);
        }

        public Declaration getReturnDecl() {
            return returnDecl;
        }
        
        public int getLineNum()
        {
        	return lineNum;
        }
        
        public boolean isVoid()
        {
            return returnDecl.getType() == BaseType.VOID;
        }
        
        public FunctionType getProtoType() {
        	List<Type> paramTypes = new ArrayList<Type>(params.size());
        	for (int i = 0, size = params.size(); i < size; i++) {
        		paramTypes.add(params.get(i).getType());
        	}
        	
        	return new FunctionType(paramTypes, returnDecl.getType());
        }

        public String getName() {
            return name;
        }

        public List<Declaration> getParams() {
            return params;
        }

        public Block getBody() {
            return body;
        }
    }
    
    /**
     * This class is only needed to pass a bunch of
     * declarations as a statement;
     */
    public static class DeclContainer extends Statement {
        private List<Declaration> declarations;
        
        public DeclContainer(List<Declaration> decls)
        {
            this.declarations = decls;
        }
        // Declarations = Declaration*
        // (a list of declarations d1, d2, ..., dn)

        public void display(int level) {
            for (Declaration dcl : declarations) {
                dcl.display(level);
            }
        }
        
        public List<Declaration> getDeclarations()
        {
            return declarations;
        }
        
        // Let's not have line numbers defined for 
        // the Declarations container:
        public int getLineNum()
        {
        	return -1;
        }
    }


    public static class Declaration {
        // Declaration = Variable v; Type t
        private final Variable   v;
        private final Type       t;
        private final Expression initValue;
        
        public Declaration(Type type, String name, int lineNum) {
            v = new Variable(name, lineNum);
            t = type;
            this.initValue = null;
        }

        public Declaration(Type type, Token id, Expression initValue) {
            v = new Variable(id);
            t = type;
            this.initValue = initValue;
        } // declaration
        
        /**
         * This way we only need to declare the "OBJECT" variables:
         */
        public Declaration(Variable var)
        {
            v = var;
            t = BaseType.OBJECT;
            this.initValue = null;
        }

        public void display(int level) {
            String msg  = t + " " + v + ((initValue != null) ? " = " : "");
            displayMsg(msg, level);
            if (initValue != null) {
                initValue.display(level + 1);
            }
        }

        public Variable getVariable() {
            return v;
        }

        public Type getType() {
            return t;
        }

        public Expression getInitValue() {
            return initValue;
        }
        
        public int getLineNum()
        {
        	return v.getLineNum();
        }
    }

    // added later by MST
    public static class MyClass {
        private final Variable   v;
        private String name;
        private List<Declaration> globals;
        private List<Function> functions;
        private Constructor constructor;
        private int lineNum;

        public MyClass(Token className, List<Declaration> globals, List<Function> functions, Constructor constructor)
        {
            this.name = className.image;
            this.globals = globals;
            this.functions = functions;
	    this.constructor = constructor;
            this.lineNum = className.beginLine;
     	    v = new Variable(name, lineNum);
        }

	public MyClass(String className, List<Declaration> globals, List<Function> functions, Constructor constructor, int lineNum)
        {
            this.name = className;
            this.globals = globals;
            this.functions = functions;
	    this.constructor = constructor;
            this.lineNum = lineNum;
     	    v = new Variable(name, lineNum);
        }

        public void display(int level) {
            Indenter indent = new Indenter(level);
            indent.display("Class: " + name);
            for (Declaration decl : globals) {
                decl.display(level + 1);
            }
            for (Function func : functions) {
                func.display(level + 1);
            }
            constructor.display(level + 1);
            Debug.println();
        }
        
        public int getNumGlobals()
        {
            return globals.size();
        }

        public List<Declaration> getGlobals() {
            return globals;
        }

        public List<Function> getFunctions() {
            return functions;
        }

  	public Constructor getConstructor() {
            return constructor;
        }

        public String getName(){
            return this.name;        
        }
        
        public Variable getVariable() {
            return v;
        }

	public int getLineNum() {
	    return lineNum;
	}
	
	public int getNumScopeVariables()
        {
        	return globals.size();
        }
        
    }
    public interface Type
    {
        boolean isBase();
        
        boolean canBeAssignedTo(Type type);
        //Type getResultType();
    }
    
    /**
     * This class encapsulates either the Base Type,
     * or function type.
     */
    public static class FunctionType implements Type {
        private final Type resultType;
        private final List<Type> paramTypes;
        private boolean  isLambda; //added later by MST
        
        public FunctionType(List<Type> paramTypes, Type returnType)
        {
	 this.paramTypes = paramTypes;
         resultType = returnType;
	 isLambda = true;
        }

	public FunctionType(List<Type> paramTypes, Type returnType, boolean isLambda) { //constructor added later by MST - for First class functions
	    this.paramTypes = paramTypes;
	    resultType = returnType;
	    this.isLambda = isLambda;
	}
        
        public boolean isBase()
        {
            return false;
        }
        
        @Override
        public boolean equals(Object o)
        {
            if (o == this) {
                return true;
            }
            if (o instanceof FunctionType == false) {
                return false;
            }
            
            FunctionType other = (FunctionType)o;
            
            return paramTypes.equals(other.paramTypes) && resultType.equals(other.resultType);
        }
        
        @Override
        public int hashCode()

        {
            return 31 * paramTypes.hashCode() + resultType.hashCode();
        }

	public boolean isLambda(){ //added later by MST
	  return isLambda;
	}
        
        @Override
        public String toString()
        {
            StringBuilder result = new StringBuilder();
            result.append("(");
            for (Type paramType : paramTypes) {
                result.append(paramType);
                if(isLambda == true)
                  result.append(", ");
	        else
	          result.append(" -> "); //added later by MST
            }
            result.append(resultType);
            result.append(")");
            return result.toString();
        }
        
        public List<Type> getParamTypes()
        {
            return paramTypes;
        }

        // @Override
        public Type getResultType() {
            return resultType;
        }
     
        public boolean canBeAssignedTo(Type to)
        {
            if (to == BaseType.OBJECT) {
                return true;
            }
            
            // Now, a Functon type can NOT be assigned to any other Base Type:
            if (to instanceof FunctionType == false) {
                return false;
            }
            
            FunctionType funcTo = (FunctionType)to;
            
            if (funcTo.paramTypes.size() != paramTypes.size()) {
                return false;
            }
            
            for (int i = 0, size = paramTypes.size(); i < size; i++) {
                if (paramTypes.get(i).canBeAssignedTo(funcTo.paramTypes.get(i)) == false) {
                    return false;
                }
            }

            // "SPECIAL FEATURE" (read HOLE): Don't check the return types;
            // otherwise, we won't be able to suppor the Lambda Expressions:
            return true;
        }
    }
    

    public enum BaseType implements Type{
        // Type = int | bool | char | float
        INT("int"), BOOL("bool"), FLOAT("float"), VOID("void"), 
        STRING("string"), LIST("list"), TUPLE("tuple"), OBJECT("object"),
        FUNCARG("funcArg");

        private final String id;

        private BaseType(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }
        
        public boolean isBase()
        {
            return true;
        }
        
        //@Override
        public Type getResultType()
        {
            return this;
        }
        
        public boolean canBeAssignedTo(Type to)
        {
            // If these two Base types are equal, return tru:
            if (this == to || to == OBJECT) {
                return true;
            }
            
            // The only other case if we are assigning from INT to FLOAT:
            if (this == INT && to == FLOAT) {
                return true;
            }
            return false;
        }
    }

    public static abstract class Statement {
        // Statement = Block | Assignment | Conditional | Loop
        // | Expression | Return
        public void display(int level) {
            displayClass(this, level);
        }
        
        // To be overridden by the classes that have
        // scope variables.
        public int getNumScopeVariables()
        {
        	return 0;
        }
        
    }
    
    public static class ConnectionToUrl extends Statement {
    	String databaseUrl; String username; String password;
    	//Connection conn = null;
    	 public ConnectionToUrl (String dbUrl, String usrnm, String psswd) {
    	// do something in here	
    		 databaseUrl = dbUrl.substring(1, dbUrl.length()-1); 
    		 username = usrnm.substring(1, usrnm.length()-1);;
    		 password = psswd.substring(1, psswd.length()-1);;
    		System.out.print("\nDATABASEURL IS: " + databaseUrl + 
    				"\nUserNAME: " + username + 
    				"\nPASSWORD: " + password);
    		System.out.println("\nNOW YOU HAVE TO CALL establishConnection");
    	}
      	
      	/* Given the URL, userName and password, this method will establish
      	 * a connection with the database at the given database URL,
      	 */
      	public Connection establishConnection() {	     	  
      		System.out.println("Current data: \ndatabaseUrl " + databaseUrl + 
    			  "\nUserName " + username+ "\nPassword "  + password);
      	  
      	String driver = "oracle.jdbc.driver.OracleDriver";
    	databaseUrl = "jdbc:oracle:thin:@rising-sun.microlab.cs.utexas.edu:1521:orcl";
   		username = "cs345_18";
    	password = "orcl_7857"; 
    	Connection conn = null;
    	try {
			Class.forName(driver);
					conn = DriverManager.getConnection(databaseUrl, username, password);
    	} catch (ClassNotFoundException e) {
			System.out.println("Connection not established. Msg from AbstractSyntax.\n");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Connection not established; SQL Exception. Msg from AbstractSyntax.\n");
			e.printStackTrace();
		}

    	return conn;
      	}
    }//end of class
    
    public static class Insert extends Statement {
    	private static String []rowToInsert = new String [3]; //has format [subject, relatiohship, object]
    	private static String dbName;
    	private static Connection c = null;
    	public Insert (String databaseName ,String subject, String relationship, String object,
    			String dbUrl, String name, String password) {
    		rowToInsert[0] = subject.substring(1, subject.length()-1);
    		rowToInsert[1] = relationship.substring(1, relationship.length()-1);
    		rowToInsert[2] = object.substring(1, object.length()-1);
    		dbName = databaseName.substring(1, databaseName.length()-1);
    		// establish connection first
    		
    		ConnectionToUrl connectionToUrl= new ConnectionToUrl (dbUrl, name, password);
    		c = connectionToUrl.establishConnection();
    		
    	}
    	
    	public void insertTripleIntoDatabase () {
    		   java.sql.Statement stmt = null;
    		   
    		   try {
    			   stmt = c.createStatement();
    			   String insertStatement = "INSERT INTO " + dbName + " VALUES(";
    			   //add all of the rows of the table to the table definition
    			   for (int currentColumn = 0; currentColumn < rowToInsert.length; currentColumn++) {
    				   insertStatement+= "'" + rowToInsert[currentColumn]+ "'";
    				   if(currentColumn < rowToInsert.length-1) insertStatement+= ", ";
    				   else insertStatement += ")";
    			   }
    			   System.out.println(insertStatement);
    			   stmt.executeUpdate(insertStatement); 
    			   // commit the transaction
    			   c.commit();
    			   // set auto commit to true (from now on every single
    			   // statement will be treated as a single transaction
    			   c.setAutoCommit(true);
    		   } catch (SQLException e) {
    			   System.err.println("Could not insert into table");
    			   e.printStackTrace();
    		   } finally {
    			   try {
    				   c.close();
    				   stmt.close();
    			   } catch (SQLException e) {
    				   System.err.println("Connection or statement could not be closed correctly");
    				   e.printStackTrace();
    			   }
    		   }//end of finally   
    	} //end of insertTripleIntoDatabase
    	
    } //end of class
    
    public static class CreateDatabase extends Statement {
    	private static String dbName;
    	public static Connection c =null;
    	public CreateDatabase (String dbName, String dbUrl, String name, String password) {
    		this.dbName = dbName.substring(1, dbName.length()-1);
    		System.out.println(dbName);
    		// establish connection first
    		System.out.println("DATA: DATA\n"+ this.dbName + "\n" + dbUrl + "\n" + name + "\n" + password);
       		ConnectionToUrl connectionToUrl= new ConnectionToUrl (dbUrl, name, password);
    		c = connectionToUrl.establishConnection();
    		createDatabase();
    		}
    	
    	public void createDatabase () {
    		java.sql.Statement stmt = null;
    		try {
    			stmt = c.createStatement();
    			String []columnNames = {"Subject", "Relationship", "Object"};
    			String tableParams = "CREATE TABLE " + dbName + "("; //name VARCHAR2(30), trick VARCHAR2(30))";

    			//add all of the rows of the table to the table definition
    			for (int currentColumn = 0; currentColumn < columnNames.length; currentColumn++) {
    				tableParams+= columnNames[currentColumn] + " VARCHAR2(30)";
    				if(currentColumn < columnNames.length-1) tableParams+= ", ";
    				else tableParams += ")";
    			}

    			stmt.executeUpdate(tableParams);
    			// commit the transaction
    			c.commit();
    			// set auto commit to true (from now on every single
    			// statement will be treated as a single transaction
    			c.setAutoCommit(true);
    		} catch (SQLException e) {
    			System.err.println("Table could not be created");
    			e.printStackTrace();
    		} finally {
    			try {
    				//connection.close();
    				stmt.close();
    				c.close();
    			} catch (SQLException e) {
    				System.err.println("Connection or statement could not be closed correctly");
    				e.printStackTrace();
    			}
    		}
    	}

    }//end of class

    public static class DisplayEntireDatabase extends Statement {
    	private static Connection c = null;
    	private static String dbName;
    	public DisplayEntireDatabase (String dbName, String dbUrl, String name, String password) {
    		this.dbName =dbName.substring(1, dbName.length()-1); //"cat_relations";
    		// establish connection first
    		System.out.println ("dbName, databaseurl, name, password\n" + this. dbName+"\n" + dbUrl + "\n" +  name +"\n" + password );
       		ConnectionToUrl connectionToUrl= new ConnectionToUrl (dbUrl, name, password);
    		c = connectionToUrl.establishConnection();
    		displayDatabase();
    		}
    	
 	   public static void displayDatabase() {
 		   //System.out.println(dbName + "\n " + );
 		  String []columnNames = {"Subject", "Relationship", "Object"};//{"Cat_Name", "Relationship", "Other_Cat_Name"};
		   ResultSet rs = null; // result set object
		   java.sql.Statement stmt = null;
		   try {
			   stmt = c.createStatement(); 
			 System.out.println("\nWe get to here and we crash after \n");
			   rs = stmt.executeQuery(constructQuery(dbName, columnNames));
			 System.out.println("\nWe get to here and we crash after \n");
			   // iterate the result set and get one row at a time
			   
			   while (rs.next()) {
				   for (int currentColumn = 0; currentColumn < columnNames.length; currentColumn++) {
					   String name = rs.getString(currentColumn+1); // 1st column in query

					   System.out.print(columnNames[currentColumn] + " = " + name);
					   System.out.print("  |  ");
				   }
				   System.out.println("==========");
			   }
		   } catch (SQLException e) {
			   e.printStackTrace();
		   } finally {
			   try {
				   c.close();
				   stmt.close();
				   rs.close();
			   } catch (SQLException e) {
				   System.err.println("Connection or statement could not be closed correctly");
				   e.printStackTrace();
			   }
		   }
	   }
    	
 	  private static String constructQuery (String databaseName, String[] columnNames) {
		   String query = "SELECT ";
		   //add all of the rows of the table to the table definition
		   for (int currentColumn = 0; currentColumn < columnNames.length; currentColumn++) {
			   query+= columnNames[currentColumn];
			   if(currentColumn < columnNames.length-1) query+= ", ";
			   else query += "";
		   }
		   
		   query+= " FROM " + databaseName;
		   return query;
	   }
    } //end of class

    /*
    public static class Skip extends Statement {
        public void display(int level) {
            super.display(level);
        }
    }
    */

    /**
     * A generator for the List Comprehension and for-each loops!
     * Note, we are currently supporting generators
     * of a single variable, or single-level tuple
     */
    public abstract static class Generator {
        private Expression list;
        
        public abstract List<Variable> getDeclaredVariables();
        
        public List<Declaration> getDeclarations()
        {
            List<Declaration> result = new ArrayList<Declaration>();
            for (Variable var: getDeclaredVariables()) {
                result.add(new Declaration(var));
            }
            return result;
        }
        
        protected Generator(Expression list) {
            this.list = list;
        }
        
        protected abstract void displayTarget(int level);
        
        public Variable getVar()
        {
            return null;
        }
        
        public List<Variable> getVarTuple()
        {
            return null;
        }
        
        public void display(int level) {
            displayClass(this, level);
            displayTarget(level + 1);
            list.display(level + 1);
        }
        
        public Expression getList()
        {
            return list;
        }
    }
    
    public static class VariableGenerator extends Generator
    {
        private Variable var;
        
        public VariableGenerator(Variable var, Expression exp)
        {
            super(exp);
            this.var = var;
        }

        @Override
        protected void displayTarget(int level) {
            var.display(level);
        }
        
        public List<Variable> getDeclaredVariables()
        {
            return Collections.singletonList(var);
        }
        
        @Override
        public Variable getVar()
        {
            return var;
        }
    }
    
    public static class TupleGenerator extends Generator
    {
        private List<Variable> varList;
        
        public TupleGenerator(List<Variable> varList, Expression exp)
        {
            super(exp);
            this.varList = varList;
            /*
            varList = new ArrayList<Variable>(tokList.size());
            for (int i = 0, size = tokList.size(); i < size; i++) {
                Token t = tokList.get(i);
                if ("_".equals(t.image)) {
                    varList.add(null);
                } else {
                    varList.add(new Variable(t));
                }
            }
            */
        }

        @Override
        protected void displayTarget(int level) {
            StringBuilder result = new StringBuilder();
            result.append("(");
            result.append(varList.get(0));
            for (int i = 1, size = varList.size(); i < size; i++) {
                result.append(", ");
                result.append(varList.get(i));
            }
            result.append(")");
            
            displayMsg(result.toString(), level);
        }

        @Override
        public List<Variable> getDeclaredVariables() {
            // We need to filter out nulls, which correspond to "_".
            List<Variable> result = new ArrayList<Variable>();
            for (Variable var : varList) {
                if (var != null) {
                    result.add(var);
                }
            }
            return result;
        }
        
        @Override
        public List<Variable> getVarTuple()
        {
            return varList;
        }
    }
    

    public static class ListComprehension extends Expression {
    	
        // Block = [ expression | generator ]
        // (a Vector of list IntValue elements)
        Expression exp;
        List<Generator> generators;
        List<Expression> conditionals;
        private int numScopeVariables;

        public ListComprehension(Expression e, List<Generator> generators, List<Expression> conditionals) {
            exp = e;
            this.generators   = generators;
            this.conditionals = conditionals;
            
            // We need to calculate the number of scope variables by adding up all the declarations
            // coming from the gernerators:
            numScopeVariables = 0;
            for (Generator gen : generators) {
                numScopeVariables += gen.getDeclaredVariables().size();
            }
        }

        public void display(int level) {
            super.display(level);
            exp.display(level + 1);
            for (Generator gen : generators) {
                gen.display(level + 1);
            }
            for (Expression exp : conditionals) {
                exp.display(level + 1);
            }
        }
              
        @Override
        public int getLineNum()
        {
        	return exp.getLineNum();
        }
        
        @Override
        public int getNumScopeVariables()
        {
            return numScopeVariables;
        }
        
        public List<Generator> getGenerators()
        {
            return generators;
        }
        
        public List<Expression> getConditionals()
        {
            return conditionals;
        }
        
        public Expression getTarget()
        {
            return exp;
        }
    }
    
    public static class LambdaDef extends Expression{    	
        private List<Variable> vars;
        private Expression exp;
        private int contextSize = -1;
        private int lineNum;

        public LambdaDef(int lambdaLineNum, List<Token> idList, Expression exp)
        {
        	lineNum =  lambdaLineNum;
        	vars = new ArrayList<Variable>(idList.size());
        	
        	for (Token tok : idList) {
        		vars.add(new Variable(tok));
        	}
        	
            this.exp = exp;
        }

        public void display (int level)
        {
            Indenter indent = new Indenter(level);
            indent.display("Lambda: ");
            displayMsg(vars.toString(), level + 1);
            exp.display(level + 1);
        }
        
        @Override
        public int getLineNum()
        {
        	return lineNum;
        }
        
        public void setContextSize(int inSize)
        {
            this.contextSize = inSize;
        }
        
        public int getContextSize()
        {
            return contextSize;
        }
        
        public List<Variable> getVars()
        {
            return vars;
        }
        
        public Expression getExpression()
        {
            return exp;
        }
        
        public Type getProtoType()
        {
            // The lambda prototype is (object, object, ...., object)
            List<Type> paramTypes = new ArrayList<Type>(vars.size());
            for (int i = 0, size = vars.size(); i < size; i++) {
                paramTypes.add(BaseType.OBJECT);
            }
            
            return new FunctionType(paramTypes, BaseType.OBJECT);
        }
    }

    public static class ShowStack extends Statement {
        public String id;

        public ShowStack(String id){
            this.id = id;
        }
    }

    public static class Block extends Statement {
        // Block = Statement*
        // (a Vector of members)
        private List<Statement> members = new ArrayList<Statement>();
        private final int numScopeVariables;
        
        public Block(List<Statement> members)
        {
            this.members = members;
            int varCount = 0;
            for (Statement s : members) {
            	if (s instanceof DeclContainer) {
            		DeclContainer declCont = (DeclContainer)s;
            		varCount += declCont.getDeclarations().size();
            	}
            }
            
            // Set the count of the Num Scoped Variables:
            numScopeVariables = varCount;
        }
        
        public List<Statement> getMembers()
        {
        	return members;
        }

        public void display(int level) {
            super.display(level);
            for (Statement s : members)
                s.display(level + 1);
        }
        
        /*
        // Where is a line number for a block???
        @Override
        public int getLineNum()
        {
        	return -1;
        }
        */
        
        @Override
        public int getNumScopeVariables()
        {
        	return numScopeVariables;
        }
    }

    public static class Assignment extends Statement {
        // Assignment = Variable target; Expression source
        private LValue target;
        private Expression source;

        public Assignment(LValue lval, Expression e) {
            target = lval;
            source = e;
        }

        public void display(int level) {
            super.display(level);
            target.display(level + 1);
            source.display(level + 1);
        }

        public LValue getTarget() {
            return target;
        }

        public Expression getSource() {
            return source;
        }
        
        /*
        @Override
        public int getLineNum()
        {
        	return target.getLineNum();
        }
        */
    }
    

    public static class Conditional extends Statement {
        // Conditional = Expression test; Statement thenbranch, elsebranch
        private Expression test;
        private Statement thenbranch, elsebranch;

        // elsebranch == null means "if... then"

        public Conditional(Expression t, Statement tp, Statement ep) {
            test = t;
            thenbranch = tp;
            elsebranch = ep; // null is equivalent to 'skip'. (ep == null) ? new Skip() : ep;
            
        }

        public void display(int level) {
            super.display(level);
            test.display(level + 1);
            thenbranch.display(level + 1);
            assert elsebranch != null : "else branch cannot be null";
            elsebranch.display(level + 1);
        }

        public Expression getTest() {
            return test;
        }

        public Statement getThenbranch() {
            return thenbranch;
        }

        public Statement getElsebranch() {
            return elsebranch;
        }
    }

    public static class Loop extends Statement {
        // Loop = Expression test; Statement body
        private Expression test;
        private Statement body;

        public Loop(Expression t, Statement b) {
            test = t;
            body = b;
        }

        public void display(int level) {
            super.display(level);
            test.display(level + 1);
            body.display(level + 1);
        }

        public Expression getTest() {
            return test;
        }

        public Statement getBody() {
            return body;
        }
    }
    
    public static class ForEach extends Statement {
        private Generator gen;
        private Statement body;
        private int numScopeVariables;
        
        public ForEach(Generator gen, Statement body)
        {
            this.gen = gen;
            this.body = body;
            this.numScopeVariables = gen.getDeclaredVariables().size();
        }
        
        public void display(int level)
        {
            super.display(level);
            gen.display(level + 1);
            body.display(level + 1);
        }
        
        @Override
        public int getNumScopeVariables()
        {
            return numScopeVariables;
        }
        
        public Statement getBody()
        {
            return body;
        }
        
        public Generator getGenerator()
        {
            return gen;
        }
    }

    public static class Return extends Statement {
        private Variable target;
        private Expression result;
        private int lineNum;

        public Return(int lineNum, String curFuncionName, Expression e) {
        	this.lineNum = lineNum;
            target = new Variable(returnName(curFuncionName), lineNum);
            result = e;
        }

        public void display(int level) {
            super.display(level);
            if (target != null)
                target.display(level + 1);
            else
                System.out.print("target: null");
            result.display(level + 1);
        }

        public Variable getTarget() {
            return target;
        }

        public Expression getResult() {
            return result;
        }
        
        public int getLineNum()
        {
        	return lineNum;
        }
    }

    //added later by MST for First class functions 
    public static class FuncArg extends Expression implements LValue{
        private String name; 
	private List<Declaration> args;
	private int lineNum;
	private Variable var;

        public FuncArg(Token t, List<Declaration> a) {
          name = t.image; 
	  lineNum = t.beginLine;
          args = a;
          var = new Variable(t);
        } 

        public void display(int level){
          super.display(level);
	  Indenter indent = new Indenter(level);
          System.out.print(name);
          indent.display(" args = ");
	  
	  for( Declaration d: args){
	    d.display(level + 1);
  	  }
        }

	public String getName(){
	  return name;
	}

	public List<Declaration> getArgs(){
	  return args;
	}

	public Variable getVar(){
	  return var;
	}

	@Override
	public int getLineNum(){
	  return lineNum;
	}
    }

    public static class Call extends Expression {
        private Variable var;
        private LambdaDef lambda;
        private List<Expression> args;
        private Function function;
        private int stackOffset;
        private int lineNum;
        
        // Indicates whether it is a call to a builtin function;
        private BuiltinFunctions.Name builtinName;


        public Call(Token t, List<Expression> a) {
        	lineNum = t.beginLine;
            args = a;
            
            // Check whether we are dealing with a built-in function:
            try {
                builtinName = BuiltinFunctions.Name.valueOf(t.image);
            } catch (IllegalArgumentException e) {
                // Ignore.
            }
            
            // If this is NOT a built-in function, we need to assign a 'var'
            if (builtinName == null) {
                var = new Variable(t);
            }
        }
        
        public Call(LambdaDef lambda, List<Expression> args)
        {
        	lineNum = lambda.getLineNum();
            this.lambda = lambda;
            this.args = args;            
        }

        public void display(int level) {
            Indenter indent = new Indenter(level);        
            if (lambda != null) {
                displayClass(this, level);
                lambda.display(level + 1);
            } else {
                String name = "INVALID";
                if (var != null) {
                    name = var.getName();
                }
                if (function != null) {
                    name = function.getName();
                }
                if (builtinName != null) {
                    name = builtinName.toString();
                }
                indent.display(getClass().getSimpleName() + ": " + name + ", stackOffset=" + stackOffset);
            }
            
            indent.display("  args = ");
            for (Expression e : args) {
                e.display(level + 1);
            }
            // indent.display("  return type = " + returnType);
        }
        
        public void setFunction(Function funct, int stackOffset)
        {
        	this.function = funct;
        	this.var = null;
        	this.stackOffset = stackOffset;
        }

	public void setStackOffset(int stackOffset){ //added later
		this.stackOffset = stackOffset;
	}

	public void setFunctWithoutOffset(Function funct){ //added later
		this.function = funct;
        	this.var = null;
	}
        
        public BuiltinFunctions.Name getBuiltinFunctName()
        {
            return builtinName;
        }
        
        public int getStackOffset()
        {
            return stackOffset;
        }
                
        public Variable getVar()
        {
        	return var;
        }
        
        public Type getReturnType()
        {
            return (builtinName != null) ? builtinName.getReturnType() : function.getReturnDecl().getType();
        }
        
        public Function getFunction()
        {
            return function;
        }
        
        public List<Expression> getArgs()
        {
            return args;
        }
        
        @Override
        public int getLineNum()
        {
        	return lineNum;
        }
        
        public LambdaDef getLambda()
        {
            return lambda;
        }
    }

   

    abstract public static class Expression extends Statement {
        // Expression = Variable | Value | Binary | Unary | TypeCast | Call

        // public abstract Type getType();    	
    	public abstract int getLineNum();
        
        public void display(int level) {
            Indenter indent = new Indenter(level);
            indent.display(getClass().getSimpleName() + ": ");
        }
        
    }
    
    public static class ListTupleReference extends Expression implements LValue {
        private final Variable listVar;
        private final Expression index;
        private final int lineNum;
        
        public ListTupleReference(Token listVar, Expression index)
        {
        	this.lineNum = listVar.beginLine;
            this.listVar = new Variable(listVar);
            this.index = index;
        }
        
        /*
        public String getListName()
        {
            return listName;
        }
        */
        
        public Variable getListVar()
        {
            return listVar;
        }
        
        public Expression getIndex()
        {
            return index;
        }

        public void display(int level) {
            super.display(level);
            System.out.print(listVar.getName() + ", index: ");
            index.display(level + 1);
        }
        
        @Override
        public int getLineNum()
        {
        	return lineNum;
        }
    }

    public static class Variable extends Expression implements LValue {
        public static enum VarType {
            GLOBAL,
            LOCAL,
            LAMBDA, 
	    INSTANCE
        };
        
        // Variable = String id
        private final String name;
        private final int lineNum;
        //private final List<Function> functions;

        private VarType varType;
        // This is only used for the variables within the
        // Lambda function and indicates the Source of these
        // variables.
        private Variable lambdaSource;
        private int address;

        public Variable(Token t) {
        	lineNum   = t.beginLine;
            name      = t.image;
        }
        
        public Variable(String name, int lineNum)
        {
        	this.lineNum = lineNum;
        	this.name    = name;
        }

             
        public void setExecutionData(Variable another)
        {
            varType = another.varType;
            address = another.address;
        }
        
        public void setExecutionData(VarType varType, int address, Variable lambdaSource)
        {
            this.varType = varType;
            this.address = address;
            this.lambdaSource = lambdaSource;
        }

        public String getName() {
            return name;
        }
        
        public VarType getVarType()
        {
            return varType;
        }
        
        public int getAddress()
        {
            return address;
        }

        /*
        public String toString() {
            return name;
        }
        */

        public boolean equals(Object obj) {
            String s = ((Variable) obj).name;
            return name.equals(s); // case-sensitive identifiers
        }

        public int hashCode() {
            return name.hashCode();
        }

        public void display(int level) {
            super.display(level);
            System.out.printf("%s, %s addr=%d", name, varType, address);
            //%s refers to LOCAL, GLOBAL, etc.
        }
        
        @Override
        public int getLineNum()
        {
        	return lineNum;
        }
        
        public Variable getLambdaSource()
        {
            return lambdaSource;
        }

        public String toString(){
            return "<" + this.getName() + 
                   ", " + this.getAddress() + 
                   ", " + this.getVarType() +
                   ">";
        }
    }
        
    public static class ListTupleExpression extends Expression {
    	private final boolean isTuple;
        private List<Expression> members;
        private int lineNum;

        public static ListTupleExpression emptyList(int lineNum)
        {
            return new ListTupleExpression(new ArrayList<Expression>(), false, lineNum); 
        }
        
        public ListTupleExpression(List<Expression> members, boolean isTuple, int lineNum)
        {
        	this.lineNum = lineNum;
        	this.isTuple = isTuple;
            this.members = members;
        }
        
        public List<Expression> getMembers()
        {
            return members;
        }
        
        public boolean isTuple()
        {
        	return isTuple;
        }
        
        public void display(int level) {
            super.display(level);
            System.out.print(isTuple ? "Tuple of: " : "List of: ");
            for (Expression member : members) {
                member.display(level + 1);
            }
        }
        
        @Override
        public int getLineNum()
        {
        	return lineNum;
        }
    }

    abstract public static class Value extends Expression {
    	
        public abstract Type getType();
        
        /**
         * Returns true for int/float values:
         */
        public boolean isNumber()
        {
            return false;
        }

        public int intValue() {
            throw exception("intValue");
        }

        public boolean boolValue() {
            throw exception("boolValue");
        }

        public float floatValue() {
            throw exception("floatValue");
        }
        
        public List<Value> subListValue() {
            throw exception("subListValue");
        }
               
        public String stringValue() {
            throw exception("stringValue");
        }
        
        // TODO: Add the Lambda value.
        

        /*
        static Value mkValue(Type type) {
            if (type == Type.INT)
                return new IntValue();
            if (type == Type.BOOL)
                return new BoolValue();
            if (type == Type.CHAR)
                return new CharValue();
            if (type == Type.FLOAT)
                return new FloatValue();
            if (type == Type.UNUSED)
                return new UnusedValue();
            if (type == Type.UNDEFINED)
                return new UndefValue();
            throw new IllegalArgumentException("Illegal type in mkValue");
        }
        */

        private UnsupportedOperationException exception(String methodName)
        {
            return new UnsupportedOperationException("Trying to get '" + 
                    methodName + "' from an object which is actually: " + 
                    getClass().getSimpleName());
        }
        
        /**
         * There shall be no Line Numbers for the values!
         */
        @Override
        public int getLineNum()
        {
        	return -1;
        }
    }

    public static class IntValue extends Value {
        private int value = 0;

        @Override
        public BaseType getType() {
            return BaseType.INT;
        }

        @Override
        public boolean isNumber()
        {
            return true;
        }
        
        public IntValue(Token t)
        {
        	value = Integer.parseInt(t.image);
        }
        
        public IntValue(int v) {
            value = v;
        }

        @Override
        public int intValue() {
            return value;
        }
        
        @Override
        public float floatValue() {
            return (float)value;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }

        @Override
        public void display(int level) {
            super.display(level);
            System.out.print(value);
        }
    }

    public static class BoolValue extends Value {
        private final boolean value;

        @Override
        public BaseType getType()
        {
            return BaseType.BOOL;
        }
        
        public BoolValue(Token t)
        { 
            this(Boolean.parseBoolean(t.image), t.beginLine);
        }
        
        public BoolValue(boolean v)
        {
        	value = v;
        }

        public BoolValue(boolean v, int lineNum) {
            value = v;
        }        

        @Override
        public boolean boolValue() {
            return value;
        }

        @Override
        public String toString() {
            return Boolean.toString(value);
        }

        @Override
        public void display(int level) {
            super.display(level);
            System.out.print(value);
        }
    }


    public static class FloatValue extends Value {
        private float value = 0;

        public BaseType getType() {
            return BaseType.FLOAT;
        }

        @Override
        public boolean isNumber()
        {
            return true;
        }

        public FloatValue(Token t)
        {
        	value = Float.parseFloat(t.image);
        }

        public FloatValue(float v) {
            value = v;
        }

        @Override
        public float floatValue() {
            return value;
        }
        
        @Override
        public int intValue() {
            return (int)value;
        }

        @Override
        public String toString() {
            return Float.toString(value);
        }

        @Override
        public void display(int level) {
            super.display(level);
            System.out.print(value);
        }
    }

    
    public static class StringValue extends Value {
        private final String value;

        @Override
        public BaseType getType() {
            return BaseType.STRING;
        }
        
        public StringValue(String str)
        {
            value = str;
        }
        
        public StringValue(Token t)
        {
            value = t.image.substring(1, t.image.length() - 1);
        }

        @Override
        public String stringValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public void display(int level) {
            super.display(level);
            System.out.print(value);
        }
    }

    
    public static class ListValue extends Value {
        private final List<Value> members;

        @Override
        public BaseType getType() {
            return BaseType.LIST;
        }
        
        public ListValue(List<Value> members)
        {
            this.members = members;
        }

        @Override
        public List<Value> subListValue() {
            return members;
        }
        
        @Override
        public String toString() {
            return members.toString();
        }

        @Override
        public void display(int level) {
            super.display(level);
            System.out.print(members);
        }
    }

    
    public static class TupleValue extends Value {
        private final List<Value> members;

        @Override
        public BaseType getType() {
            return BaseType.TUPLE;
        }
        
        public TupleValue(List<Value> members)
        {
            this.members = members;
        }

        @Override
        public List<Value> subListValue() {
            return members;
        }

        @Override
        public String toString() {
            return members.toString();
        }

        @Override
        public void display(int level) {
            super.display(level);
            System.out.print(members);
        }
    }

    public static class FuncArgValue extends Value {

	String methodName;
	List<Declaration> methodArgs;

        @Override
        public BaseType getType() {
            return BaseType.FUNCARG;
        }    


        public FuncArgValue(String name, List<Declaration> args) {
	    methodName = name;
	    methodArgs = args;
        }

	public String getMethodName(){
	  return methodName;
	}
        
	public List<Declaration> getMethodArgs(){
	  return methodArgs;
	}
    }

    /*
    public static class UnusedValue extends Value {
        UnusedValue() {
            type = Type.UNUSED;
            undef = true;
        }

        public String toString() {
            return "unused";
        }

        public void display(int level) {
            super.display(level);
            System.out.print("unused");
        }
    }

    public static class UndefValue extends Value {
        public UndefValue() {
            type = Type.UNDEFINED;
            undef = true;
        }

        public String toString() {
            return "undef";
        }

        public void display(int level) {
            super.display(level);
            System.out.print("undef");
        }
    }
    */

    public static class Binary extends Expression {
        // Binary = Operator op; Expression term1, term2
        private Operator op;
        private int lineNum;
        private Expression term1, term2;

        public Binary(OpTokenPair opNum, Expression l, Expression r) {
        	this.lineNum = opNum.getLineNum();
            op = opNum.getOp();
            term1 = l;
            term2 = r;
        } // binary

        public void display(int level) {
            super.display(level);
            op.display(level + 1);
            term1.display(level + 1);
            term2.display(level + 1);
        }

        public Operator getOp() {
            return op;
        }
        
        /**
         * This is needed when we convert from generic 
         * operators into the specfic ones.
         */
        public void setOp(Operator op) {
        	this.op = op;
        }

        public Expression getTerm1() {
            return term1;
        }

        public Expression getTerm2() {
            return term2;
        }
        
        @Override
        public int getLineNum()
        {
        	return lineNum;
        }
    }

    public static class Unary extends Expression {
        // Unary = Operator op; Expression term
        private Operator op;
        private Expression term;
        private int lineNum;

        public Unary(OpTokenPair opNum, Expression e) {
        	this.lineNum = opNum.getLineNum();
        	Operator o = opNum.getOp();
            op = o == Operator.MINUS ? Operator.NEG : o;
            term = e;
        } // unary

        public void display(int level) {
            super.display(level);
            op.display(level + 1);
            term.display(level + 1);
        }

        public Operator getOp() {
            return op;
        }
        
        public void setOp(Operator newOp) {
        	this.op = newOp;
        }

        public Expression getTerm() {
            return term;
        }
        
        @Override
        public int getLineNum()
        {
        	return lineNum;
        }
    }
    
    /**
     * The type cast will NOT be a special case of a unary
     * expression, but will be a thing on its own.
     *
     * @author miakhiae
     * 
     * TODO: Implement the type cast

     * 
     */
    /*
    public static class TypeCast extends Expression {   	
    }
    */
    
    public static class OpTokenPair {
    	private Operator op;
		private int lineNum;
    	
    	public OpTokenPair(Operator op, Token t)
    	{
    		this.op = op;
    		this.lineNum = t.beginLine;
    	}

    	public Operator getOp() {
			return op;
		}

		public int getLineNum() {
			return lineNum;
		}
    }

    public static enum Operator {
        AND("&&"), 
        OR("||"),

        LT("<"), 
        LE("<="), 
        EQ("=="), 
        NE("!="), 
        GT(">"), 
        GE(">="),
        // ArithmeticOp = + | - | * | /
        PLUS("+"), 
        MINUS("-"), 
        TIMES("*"), 
        DIV("/"), 
        MOD("%"),
        // UnaryOp = !
        NOT("!"), 
        NEG("neg"),
        // CastOp = int | float | char
        INT("int"), 
        FLOAT("float"), 

        // Typed Operators
        // RelationalOp = < | <= | == | != | >= | >
        INT_LT("INT<"), 
        INT_LE("INT<="), 
        INT_EQ("INT=="),
        INT_NE("INT!="), 
        INT_GT("INT>"), 
        INT_GE("INT>="),
        // ArithmeticOp(+ | - | * | /
        INT_PLUS("INT+"), 
        INT_MINUS("INT-"), 
        INT_TIMES("INT*"), 
        INT_DIV("INT/"),
        INT_MOD("INT%"),
        // UnaryOp(!
        INT_NEG("INTNEG"),
        // RelationalOp(< | <= | == | != | >= | >
        FLOAT_LT("FLOAT<"), 
        FLOAT_LE("FLOAT<="), 
        FLOAT_EQ("FLOAT=="), 
        FLOAT_NE("FLOAT!="), 
        FLOAT_GT("FLOAT>"), 
        FLOAT_GE("FLOAT>="),
        // ArithmeticOp(+ | - | * | /
        FLOAT_PLUS("FLOAT+"), 
        FLOAT_MINUS("FLOAT-"), 
        FLOAT_TIMES("FLOAT*"),
        FLOAT_DIV("FLOAT/"),
        FLOAT_MOD("FLOAT%"),
        // UnaryOp(!
        FLOAT_NEG("FLOATNEG"),
        /*
        // RelationalOp(< | <= | == | != | >= | >
        CHAR_LT("CHAR<"), 
        CHAR_LE("CHAR<="), 
        CHAR_EQ("CHAR=="),
        CHAR_NE("CHAR!="), 
        CHAR_GT("CHAR>"),
        CHAR_GE("CHAR>="),
        */
        // For Booleans, we can only do == and !=
        //  However, we do need to support &&, ||, and !
        BOOL_EQ("BOOL=="),
        BOOL_NE("BOOL!="),
        BOOL_AND("BOOL&&"),
        BOOL_OR("BOOL||"),
        BOOL_NOT("BOOL!"),

        // String operations:
        STRING_LT("INT<"), 
        STRING_LE("INT<="), 
        STRING_EQ("INT=="),
        STRING_NE("INT!="), 
        STRING_GT("INT>"), 
        STRING_GE("INT>="),
        // ArithmeticOp(+ | - | * | /
        STRING_PLUS("INT+"), 
        
        // Object - related operators:
        /* TO BE REMOVED: The generic operators will fill that role !!!
        OBJECT_LT("OBJECT<"), 
        OBJECT_LE("OBJECT<="), 
        OBJECT_EQ("OBJECT=="), 
        OBJECT_NE("OBJECT!="), 
        OBJECT_GT("OBJECT>"), 
        OBJECT_GE("OBJECT>="),
        // ArithmeticOp(+ | - | * | /
        OBJECT_PLUS("OBJECT+"), 
        OBJECT_MINUS("OBJECT-"), 
        OBJECT_TIMES("OBJECT*"),
        OBJECT_DIV("OBJECT/"),
        OBJECT_MOD("OBJECT%"),
        // UnaryOp(!
        OBJECT_NEG("OBJECTNEG"),
        */
        
        // Type specific cast
        O2ANY("O2ANY"),
        I2F("I2F"), 
        F2I("F2I");
        
        private static final String SEP = "_";
        private final String str;
        private final boolean isObject;
        
        private boolean boolResult = false;

        private Operator(String str) {
            this.str = str;
            isObject = (name().indexOf(SEP) < 0);
            
        }
        
        private static boolean isBoolResult(Operator op)
        {
            switch (op) {
            case AND: 
            case OR:
            case LT: 
            case LE: 
            case EQ: 
            case NE: 
            case GT: 
            case GE:
            case NOT:
                return true;
            default:
                return false;
            }
        }
        
        static {
            for (Operator op : Operator.values()) {
                String name = op.name();
                
                // First, try to set the Bool Result based on the Operator itself:
                op.boolResult = Operator.isBoolResult(op);
               
                // That's what is called right naming convention:
                int sepIdx = name.indexOf(SEP);
                if (sepIdx >= 0) {
                    BaseType type = BaseType.valueOf(name.substring(0, sepIdx));
                    
                    Operator baseOp = Operator.valueOf(name.substring(sepIdx
                            + SEP.length()));
                    
                    // If the Base Operator gives a bool result, that's what
                    // we need to set the current operator to:
                    op.boolResult = Operator.isBoolResult(baseOp);
                    
                    if (Mapper.typeMaps.get(type).put(baseOp, op) != null) {
                        throw new UnsupportedOperationException(
                                "Redundant mapping detected by: " + op);
                    }
                }
            }
        }

        public void display(int level) {
            Indenter indent = new Indenter(level);
            indent.display(getClass().getSimpleName() + ": " + str);
        }

        @Override
        public String toString() {
            return str;
        }
        
        public boolean isObject(){
            return isObject;
        }
        
        public boolean isBoolResult() {
            return boolResult;
        }
        
        public Operator mapToType(Type from)
        {
            Map<Operator, Operator> opMap = Mapper.typeMaps.get(from);
            return (opMap == null) ? null : opMap.get(this);
        }

        private static class Mapper {
            private static final Map<BaseType, Map<Operator, Operator>> typeMaps;
            static {
                typeMaps = new EnumMap<BaseType, Map<Operator, Operator>>(
                        BaseType.class);
                for (BaseType type : BaseType.values()) {
                    typeMaps.put(type, new EnumMap<Operator, Operator>(
                            Operator.class));
                }
            }
        }
    }
}
