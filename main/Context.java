package main;

import java.util.ArrayList;
import java.util.Arrays;

import tree.Statement;

public class Context {
    
    private Context parent;
    private ArrayList<Context> children;
    private ArrayList<Statement> sequence;
    private ArrayList<Object> variables;
    //private HashMap<String, Integer> variablesIndices;

    public Context() {
        this(new Statement[0]);
    }

    public Context(Statement[] sequence) {
        this.children = new ArrayList<>();
        this.sequence = new ArrayList<>(Arrays.asList(sequence));
        this.variables = new ArrayList<>();
    }

    public void addStatement(Statement s) {
        sequence.add(s);
    }

    public Context addSubContext(Context c) {
        children.add(c);
        c.parent = this;
        return this;
    }

    public void setVarData(int varIndex, Object data) {
        while (varIndex >= variables.size()) {
            variables.add(null);
        }
        variables.set(varIndex, data);
    }

    /*
    public int varIndex(String name) {
        return variablesIndices.getOrDefault(name, -1);
    }

    public String varName(int i) {
        return variables.get(i).first;
    }
    */

    public Object varData(int i) {
        return variables.get(i);
    }
}
