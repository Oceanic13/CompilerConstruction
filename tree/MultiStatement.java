package tree;

import java.util.ArrayList;
import java.util.Arrays;

import main.Scope;
import utils.ReturnValue;
import utils.NullObj;

public class MultiStatement extends Statement {

    private ArrayList<Statement> sequence;

    public MultiStatement(Scope scope, Statement...sequence) {
        super(scope);
        this.sequence = new ArrayList<>(Arrays.asList(sequence));
    }

    public void add(Statement...statements) {
        for (var s : statements) {sequence.add(s);}
    }

    @Override
    public Object eval() {
        for (var s : sequence) {
            var v = s.eval();
            if (v.getClass() == ReturnValue.class) {
                return v;
            }
        }
        return NullObj.get();
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
