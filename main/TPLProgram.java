package main;

import java.util.HashMap;

import tree.MultiStatement;
import tree.Statement;
import utils.NullObj;

public class TPLProgram {

    // TODO: objects
    
    private MultiStatement root;
    //private ArrayList<Object> variables;
   private TPLScope scope;
   private Object returnValue;

   // Functions and Objects are always global Scope
   private HashMap<String, ITPLFunction> funcs;
   private HashMap<String, TPLClass> classes;

    public TPLProgram() {
        this.scope = new TPLScope(this, null, false);
        this.root = new MultiStatement(this);
        this.funcs = new HashMap<>();
        initSystemFuncs();
    }

    public TPLProgram(Statement sequence) {
        this.scope = new TPLScope(this, null, false);
        this.root = new MultiStatement(this, sequence);
        this.funcs = new HashMap<>();
        initSystemFuncs();
    }

    public TPLScope enterScope(boolean canSeeParent) {
        this.scope = new TPLScope(this, scope, canSeeParent);
        return this.scope;
    }

    public TPLScope leaveScope() {
        assert(!scope.isRootScope());
        this.scope = scope.PARENT;
        return this.scope;
    }

    private void initSystemFuncs() {
        funcs.put("len", TPLSystemFunction.LENGTH);
        funcs.put("random", TPLSystemFunction.RANDOM);
    }

    public void addStatement(Statement s) {
        root.add(s);
    }

    public boolean isFuncDeclared(String name) {
        return funcs.containsKey(name);
    }

    public void declFunc(String name, String[] argsNames, Statement seq) {
        if (isFuncDeclared(name)) {
            System.err.printf("Function %s has already been declared\n", name);
            return;
        }
        funcs.put(name, new TPLFunction(name, argsNames, seq));
    }

    public boolean isClassDeclared(String name) {
        return classes.containsKey(name);
    }

    public void declClass(String name, TPLClass tplClass) {
        if (isClassDeclared(name)) {
            System.err.printf("Class %s has already been declared\n", name);
            return;
        }
        classes.put(name, new TPLClass(this, name));
    }

    public Object callFunc(String name, Object[] argsValues) throws Exception {
        var f = funcs.getOrDefault(name, null);
        if (f==null) {System.err.printf("Cannot call undeclared function %s\n", name); return NullObj.get();}
        return f.eval(argsValues);
    }

    public TPLScope getScope() {
        return scope;
    }

    public void execute() {
        try {
            returnValue = root.eval();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append("===========================================\n");
        b.append("PROGRAM\n");
        b.append("Scope------------------------------------------\n");
        b.append(scope.toString());
        b.append("Functions--------------------------------------\n");
        b.append(funcs.toString()+'\n');
        b.append("Tree-------------------------------------------\n");
        b.append(root.toString()+'\n');
        b.append("Returns----------------------------------------\n");
        b.append(returnValue);
        b.append("\n===========================================\n");
        return b.toString();
    }
}
