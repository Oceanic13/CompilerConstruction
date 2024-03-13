package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import structs.DataValue;
import tree.Statement;
import utils.Pair;

public class Context {
    
    private Context parent;
    private ArrayList<Context> children;
    private ArrayList<Statement> sequence;
    private ArrayList<Pair<String, DataValue<?>>> variables;
    private HashMap<String, Integer> variablesIndices;

    public Context() {
        this(new Statement[0]);
    }

    public Context(Statement[] sequence) {
        this.children = new ArrayList<>();
        this.sequence = new ArrayList<>(Arrays.asList(sequence));
    }

    public void addStatement(Statement s) {
        sequence.add(s);
    }

    public Context addSubContext(Context c) {
        children.add(c);
        c.parent = this;
        return this;
    }

    public void setVarData(int varIndex, DataValue<?> data) {
        variables.get(varIndex).second = data;
    }

    public int varIndex(String name) {
        return variablesIndices.getOrDefault(name, -1);
    }

    public String varName(int i) {
        return variables.get(i).first;
    }

    public DataValue<?> varData(int i) {
        return variables.get(i).second;
    }
}
