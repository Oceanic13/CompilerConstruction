package tree;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Data;
import utils.Pair;

public class Program {
    
    private Node root;
    private ArrayList<Pair<String, Data>> variables;
    private HashMap<String, Integer> variablesIndices;

    public void setVarData(int varIndex, Data data) {
        variables.get(varIndex).second = data;
    }

    public int varIndex(String name) {
        return variablesIndices.getOrDefault(name, -1);
    }

    public String varName(int i) {
        return variables.get(i).first;
    }

    public Data varData(int i) {
        return variables.get(i).second;
    }
}
