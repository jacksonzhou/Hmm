package proj;

import java.util.HashMap;

import proj.AbstractSyntax.Variable;
import proj.AbstractSyntax.Value;

public class ActivationRecord{

    private HashMap<Variable, Value> varValues;
    //also map address to value?
    private Value return_val;

    public ActivationRecord(){
        varValues = new HashMap<Variable, Value>();
        return_val = null;
    }

    public void addVarValue(Variable var, Value val){
        varValues.put(var, val);
    }

    public String toString(){
        String temp = "";
        for(Variable var : varValues.keySet()){
            temp += "var: " + var.toString() +
                    "val: " + varValues.get(var).toString() +
                    "\n";
        }

        return temp;
    }

    
}
