package main;

import java.util.HashMap;

import tree.Statement;
import utils.NullObj;

public class TPLClass {
    
    public final String NAME;
    private TPLScope instanceScope;
    private HashMap<String, ITPLFunction> instanceFunctions;

    public TPLClass(TPLProgram program, String name) {
        this.NAME = name;
        this.instanceScope = new TPLScope(program, null, false);
        this.instanceFunctions = new HashMap<>();
    }

    public boolean isFuncDeclared(String name) {
        return instanceFunctions.containsKey(name);
    }

    public void declFunc(String name, String[] argsNames, Statement seq) {
        if (isFuncDeclared(name)) {
            System.err.printf("Function %s has already been declared for class %s\n", name, NAME);
            return;
        }
        instanceFunctions.put(name, new TPLFunction(name, argsNames, seq));
    }

    public Object callFunc(String name, Object[] argsValues) throws Exception {
        var f = instanceFunctions.getOrDefault(name, null);
        if (f==null) {System.err.printf("Cannot call undeclared function %s for class %s\n", name, NAME); return NullObj.get();}
        return f.eval(instanceScope, argsValues);
    }
}
