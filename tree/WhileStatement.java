package tree;

import main.Program;
import utils.DataType;

public class WhileStatement extends Statement {

    private Expr condition;
    private MultiStatement sequence;

    public WhileStatement(Expr condition, Statement... sequence) {
        super();
        this.condition = condition;
        this.sequence = new MultiStatement(sequence);
    }

    @Override
    public void execute(Program context) {
        while (DataType.cast(condition.eval(context), Boolean.class)) {
            sequence.execute(context);
        }
    }
    
    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("WHILE <%s> {\n%s\n}", condition.toString(), sequence.toString()));
        return b.toString();
    }
}
