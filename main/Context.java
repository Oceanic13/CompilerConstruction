package main;

import java.util.ArrayList;
import java.util.HashMap;

import structs.DataType;
import tree.Node;
import utils.Pair;

public class Context {
    
    private Node root;
    private ArrayList<Pair<String, DataType<?>>> variables;
    private HashMap<String, Integer> variablesIndices;

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
