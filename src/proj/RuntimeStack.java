package proj;

import java.util.LinkedList;
import java.util.HashMap;

import proj.AbstractSyntax.Variable;
import proj.AbstractSyntax.Value;

public class RuntimeStack{

    private HashMap<Variable, Value> globalVarValues;
    private LinkedList<ActivationRecord> activationRecords;

    public RuntimeStack(){
        globalVarValues = new HashMap<Variable, Value>();
        activationRecords = new LinkedList<ActivationRecord>();
    }

    public void addRecord(ActivationRecord ar){
        activationRecords.addFirst(ar);

        printStack();
    }

    public ActivationRecord getRecord(){
        return activationRecords.getFirst();
    }

    public void printStack(){
        for(ActivationRecord ar : activationRecords){
            System.out.println("=========");
            System.out.println(ar.toString());
            System.out.println("=========");
        }
    }

}
