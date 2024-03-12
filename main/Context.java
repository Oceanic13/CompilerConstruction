package main;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import structs.DataType;
import tree.Node;
import utils.Pair;

public class Context {
    
    private Context parent;
    private ArrayList<Context> children;
    private Statement[] sequence;
    private ArrayList<Pair<String, DataType<?>>> variables;
    private HashMap<String, Integer> variablesIndices;

    public Context(Statement[] sequence) {
        this.children = new ArrayList<>();
        this.sequence = sequence;
    }

    public Context addChildren(Context...cs) {
        for (var c : cs) {
            children.add(c);
            c.parent = this;
        }
        return this;
    }

    public void setVarData(int varIndex, DataType<?> data) {
        variables.get(varIndex).second = data;
    }

    public int varIndex(String name) {
        return variablesIndices.getOrDefault(name, -1);
    }

    public String varName(int i) {
        return variables.get(i).first;
    }

    public DataType<?> varData(int i) {
        return variables.get(i).second;
    }
}
