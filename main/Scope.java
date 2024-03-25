package main;

import java.util.HashMap;
import java.util.HashSet;

import tree.Statement;
import utils.NullObj;
import utils.Pair;

/**
 * Represents an "area of code" in which certain variables are defined. Each scope
 * can have multiple chuldren scopes. A scope can access its own variables and recursively 
 * the variables of its parent but not of its children.
 * 
 * A child scope is created within:
 * braces {} or within the body of a FOR, WHILE, IF, ELSE
 */
public class Scope {
    
    private Scope parent;
    private HashSet<Scope> children;
    private HashMap<String, Object> vars;
    private HashMap<String, Pair<String[], Statement>> funcs;

    public Scope() {
        this(null);
    }

    public Scope(Scope parent) {
        this.vars = new HashMap<>();
        this.funcs = new HashMap<>();
        this.children = new HashSet<>();
        if (parent != null)
            parent.addChild(this);
    }

    public void addChild(Scope scope) {
        this.children.add(scope);
        scope.parent = this;
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
            System.err.printf("Variable %s has already been declared in the current scope %s\n", name, this.hashCode());
            return;
        }
        System.out.printf("%s := %s in %s\n", name, value, this.hashCode());
        vars.put(name, value);
    }

    public Object readVar(String name) {
        var scope = getVarOrigin(name);
        if (scope == null) {System.err.printf("Cannot read undeclared variable %s in %s\n", name, this.hashCode()); return NullObj.get();}
        return scope.vars.get(name);
    }

    public void writeVar(String name, Object value) {
        var scope = getVarOrigin(name);
        if (scope == null) {System.err.printf("Cannot write undeclared variable %s in %s\n", name, this.hashCode()); return;}
        scope.vars.put(name, value);
    }

    public Scope getFuncOrigin(String name) {
        if (funcs.containsKey(name)) return this;
        if (parent == null) return null;
        return parent.getFuncOrigin(name);
    }

    public boolean isFuncDeclared(String name) {
        return getFuncOrigin(name) != null;
    }

    public void declFunc(String name, String[] argsNames, Statement seq) {
        if (isFuncDeclared(name)) {
            System.err.printf("Function %s has already been declared in the current scope %s\n", name, this.hashCode());
            return;
        }
        funcs.put(name, new Pair<>(argsNames, seq));
    }

    public Object callFunc(String name, Object[] argsValues) {
        var scope = getFuncOrigin(name);
        if (scope == null) {System.err.printf("Cannot call undeclared dunction %s in %s\n", name, this.hashCode());  NullObj.get();}
        var f = scope.funcs.get(name);
        var argsNames = f.first;
        int n = argsNames.length;
        var seq = f.second;
        seq.SCOPE.clear();
        assert(n == argsValues.length);
        for (int i = 0; i < n; ++i) {
            seq.SCOPE.declVar(argsNames[i], argsValues[i]);
        }
        return seq.eval();
    }

    public void clear() {
        vars.clear();
        funcs.clear();
        for (var s : children) s.clear();
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("Scope(%s):%s\nVariables\n", this.hashCode(), (parent==null)? "" : parent.hashCode()));
        for (var v : vars.keySet()) {
            var o = vars.get(v);
            b.append(String.format("%s = (%s, %s)\n", v, o.getClass().getSimpleName(), o.toString()));
        }
        b.append("Functions\n");
        for (var v : funcs.keySet()) {
            b.append(String.format("%s\n", v));
        }

        b.append(String.format("\n%s\n", children.toString()));
        return b.toString();
    }


    public static class NullScope extends Scope {
        private static NullScope instance;
        private NullScope() {}
        public static NullScope get() {
            if (instance==null)instance=new NullScope();
            return instance;
        }
    }
}
