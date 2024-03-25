package main;

import tree.MultiStatement;
import tree.Statement;

public class Program {

    // TODO: functions, objects
    
    private MultiStatement root;
    //private ArrayList<Object> variables;
   private Scope scope;

    public Program() {
        this(new Scope(null));
    }

    public Program(Scope scope) {
        this(scope, new MultiStatement(scope));
    }

    public Program(Scope scope, Statement sequence) {
        this.scope = scope;
        this.root = new MultiStatement(scope, sequence);
        //this.variables = new ArrayList<>();
    }

    public void addStatement(Statement s) {
        root.add(s);
    }

    /*
    public void setVarValue(int varIndex, Object data) {
        while (varIndex >= variables.size()) {
            variables.add(null);
        }
        variables.set(varIndex, data);
    }

    public Object getVarValue(int i) {
        return variables.get(i);
    }
    */

    public Scope getScope() {
        return scope;
    }

    public void execute() {
        root.execute();
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append("===========================================\n");
        b.append("PROGRAM\n");
        b.append("-------------------------------------------\n");
        b.append(scope.toString());
        b.append("-------------------------------------------\n");
        b.append(root.toString()+'\n');
        b.append("===========================================\n");
        return b.toString();
    }
}
