package main;

import java.util.ArrayList;

import tree.MultiStatement;
import tree.Statement;

public class Program {
    
    private MultiStatement root;
    private ArrayList<Object> variables;

    public Program() {
        this(new Statement[0]);
    }

    public Program(Statement[] sequence) {
        this.root = new MultiStatement(sequence);
        this.variables = new ArrayList<>();
    }

    public void addStatement(Statement s) {
        root.add(s);
    }

    public void setVarValue(int varIndex, Object data) {
        while (varIndex >= variables.size()) {
            variables.add(null);
        }
        variables.set(varIndex, data);
    }

    public Object getVarValue(int i) {
        return variables.get(i);
    }

    public void execute() {
        root.execute(this);
    }
}
