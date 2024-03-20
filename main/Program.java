package main;

import java.util.ArrayList;

import tree.MultiStatement;
import tree.Statement;

public class Program {

    // TODO: functions, objects
    
    private MultiStatement root;
    private ArrayList<Object> variables;

    public Program() {
        this(new MultiStatement());
    }

    public Program(Statement sequence) {
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

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append("===========================================\n");
        b.append("PROGRAM\n");
        b.append("-------------------------------------------\n");
        for (int i = 0; i < variables.size(); ++i) {
            var v = getVarValue(i);
            if (v == null) {
                b.append(String.format("VAR %d : <NULL, null>\n", i));
            } else {
                b.append(String.format("VAR %d : <%s, %s>\n", i, v.getClass().getSimpleName(), v.toString()));
            }
        }
        b.append("-------------------------------------------\n");
        b.append(root.toString()+'\n');
        b.append("===========================================\n");
        return b.toString();
    }
}
