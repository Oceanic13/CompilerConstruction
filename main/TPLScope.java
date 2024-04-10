package main;

import java.util.HashMap;
import utils.NullObj;

/**
 * Represents an "area of code" in which certain variables are defined. Each scope
 * can have multiple chuldren scopes. A scope can access its own variables and recursively 
 * the variables of its parent but not of its children.
 * 
 * A child scope is created within:
 * braces {} or within the body of a FOR, WHILE, IF, ELSE
 */
public class TPLScope {
    
    public final TPLProgram PROGRAM;
    public final TPLScope PARENT;
    private boolean canSeeParent;
    private HashMap<String, Object> vars;

    public TPLScope(TPLProgram program, TPLScope parent, boolean canSeeParent) {
        this.PROGRAM = program;
        this.PARENT = parent;
        this.vars = new HashMap<>();
        this.canSeeParent = canSeeParent;
    }

    public TPLScope(TPLScope parent, boolean canSeeParent) {
        this(parent.PROGRAM, parent, canSeeParent);
    }

    public TPLScope() {
        this(null,null,false);
    }

    public TPLScope getVarOrigin(String name) {
        if (vars.containsKey(name)) return this;
        if (PARENT == null || !canSeeParent) return null;
        return PARENT.getVarOrigin(name);
    }

    public boolean varIsDeclared(String name) {
        return getVarOrigin(name) != null;
    }

    public void declVar(String name, Object value) {
        //System.out.printf("Trying to declare variable %s as %s in:%s\n", name, value, this);
        if (varIsDeclared(name)) {
            System.err.printf("Variable %s has already been declared in the current scope %s\n", name, this.hashCode());
            return;
        }
        vars.put(name, value);
    }

    public Object readVar(String name) {
        //System.out.printf("Trying to read variable %s in:%s\n", name, this);
        var scope = getVarOrigin(name);
        if (scope == null) {System.err.printf("Cannot read undeclared variable %s in %s\n", name, this.hashCode()); return NullObj.get();}
        return scope.vars.get(name);
    }

    public void writeVar(String name, Object value) {
        //System.out.printf("Trying to assign %s to variable %s in:%s\n", value, name, this);
        var scope = getVarOrigin(name);
        if (scope == null) {System.err.printf("Cannot write undeclared variable %s in %s\n", name, this.hashCode()); return;}
        scope.vars.put(name, value);
    }

    public void clear() {
        vars.clear();
    }

    /*
    public void detach() {
        if (parent != null) parent.children.remove(this);
    }
    */

    public boolean isRootScope() {
        return PARENT==null || !canSeeParent;
    }

    /*
    public Scope copy(Scope parent) {
        var c = new Scope(PROGRAM, parent);
        System.out.printf("Copy scope structure %s to scope %s\n", this.hashCode(), c.hashCode());
        for (var s : children) {
            c.children.add(s.copy(c));
        }
        return c;
    }
    */

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("Scope(%s):%s\nVariables\n", this.hashCode(), (PARENT==null)? "" : PARENT.hashCode()));
        for (var v : vars.keySet()) {
            var o = vars.get(v);
            b.append(String.format("%s = (%s, %s)\n", v, o.getClass().getSimpleName(), o.toString()));
        }
        return b.toString();
    }


    public static class NullScope extends TPLScope {
        private static NullScope instance;
        private NullScope() {
            super(null,null,false);
        }
        public static NullScope get() {
            if (instance==null)instance=new NullScope();
            return instance;
        }
    }
}
