package tree;

import java.util.ArrayList;
import java.util.Arrays;

import main.Program;

public class MultiStatement extends Statement {

    private ArrayList<Statement> sequence;

    public MultiStatement(Statement...sequence) {
        super();
        this.sequence = new ArrayList<>(Arrays.asList(sequence));
    }

    public void add(Statement...statements) {
        for (var s : statements) sequence.add(s);
    }

    @Override
    public void execute() {
        for (var s : sequence) s.execute();
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (var s : sequence) {
            b.append(s.toString());
            b.append('\n');
        }
        b.deleteCharAt(b.length()-1);
        return b.toString();
    }
}
