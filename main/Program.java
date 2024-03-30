package main;

import java.util.HashMap;

import main.Scope.NullScope;
import tree.MultiStatement;
import tree.NullStatement;
import tree.Statement;
import utils.NullObj;
import utils.Pair;

public class Program {

    // TODO: functions, objects
    
    private MultiStatement root;
    //private ArrayList<Object> variables;
   private Scope scope;
   private Object returnValue;

   // Functions and Objects are always global Scope
   private HashMap<String, IFunction> funcs;

    public Program() {
        this.scope = new Scope(this);
        this.root = new MultiStatement(scope);
        this.funcs = new HashMap<>();
        initSystemFuncs();
    }

    public Program(Statement sequence) {
        this.scope = new Scope(this);
        this.root = new MultiStatement(scope, sequence);
        this.funcs = new HashMap<>();
        initSystemFuncs();
    }

    private void initSystemFuncs() {
        funcs.put("len", SystemFunction.LENGTH);
        funcs.put("random", SystemFunction.RANDOM);
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
        funcs.put(name, new Function(name, argsNames, seq));
    }

    public Object callFunc(String name, Object[] argsValues) throws Exception {
        var f = funcs.getOrDefault(name, null);
        if (f==null) {System.err.printf("Cannot call undeclared function %s\n", name); return NullObj.get();}
        return f.eval(argsValues);
    }

    public Scope getScope() {
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
