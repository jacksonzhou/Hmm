package proj;

import java.util.HashMap;

import proj.AbstractSyntax.Variable;
import proj.AbstractSyntax.Value;

public class ActivationRecord{

    private String callerName;
    private HashMap<Variable, Value> varValues;
    //also map address to value?
    private Value return_val;

    public ActivationRecord(String callerName){
        varValues = new HashMap<Variable, Value>();
        return_val = null;
        this.callerName = callerName;
    }

    public void addVarValue(Variable var, Value val){
        varValues.put(var, val);
    }

    public Value getVarValue(Variable var){
        return varValues.get(var);
    }

    public void setReturn(Value val){
        this.return_val = val;
    }

    public String toString(){
        String temp = "=========================\n" +
                      "ar for <" + callerName + ">\n" + 
                      "=========================\n";

        temp += "RETURN VALUE: " + this.return_val + "\n";

        for(Variable var : varValues.keySet()){
            temp += "var: " + var.toString() +
                    "   val: " + ((varValues.get(var) != null ) ? varValues.get(var).toString() : "null") +
                    "\n";
        }

        return temp;
    }

    
}
