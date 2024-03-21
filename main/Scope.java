package main;

import java.util.HashMap;

public class Scope {
    
    private Scope parent;
    private HashMap<String, Object> vars;
    //private HashMap<String, Program> funcs;

    public Scope() {
        this(null);
    }

    public Scope(Scope parent) {
        this.parent = parent;
        this.vars = new HashMap<>();
    }

    public Scope getVarOrigin(String name) {
        if (vars.containsKey(name)) return this;
        if (parent == null) return null;
        return parent.getVarOrigin(name);
    }

    public boolean varIsDeclared(String name) {
        return getVarOrigin(name) != null;
    }

    public void declVar(String name, Object value) {
        if (varIsDeclared(name)) {
            System.err.printf("Variable %s has already been declared in the current scope!\n", name);
            return;
        }
        vars.put(name, value);
    }

    public Object readVar(String name) {
        var scope = getVarOrigin(name);
        if (scope == null) {System.err.printf("Cannot read undeclared variable %s!\n", name); return null;}
        return scope.vars.get(name);
    }

    public void writeVar(String name, Object value) {
        var scope = getVarOrigin(name);
        if (scope == null) {System.err.printf("Cannot write undeclared variable %s!\n", name); return;}
        scope.vars.put(name, value);
    }
}
