package tree;

import utils.DataType;

public class WhileStatement extends Statement {

    private Expr condition;
    private Statement sequence;

    public WhileStatement(Expr condition, Statement sequence) {
        super();
        this.condition = condition;
        this.sequence = sequence;
    }

    @Override
    public void execute() {
        while (DataType.cast(condition.eval(), Boolean.class)) {
            sequence.execute();
        }
    }
    
    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("WHILE <%s> {\n%s\n}", condition.toString(), sequence.toString()));
        return b.toString();
    }
}
