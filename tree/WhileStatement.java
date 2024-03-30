package tree;

import utils.DataType;
import utils.ReturnValue;
import utils.NullObj;

public class WhileStatement extends Statement {

    private Expr condition;
    private Statement sequence;

    public WhileStatement(Expr condition, Statement sequence) {
        super(condition.getScope());
        this.condition = condition;
        this.sequence = sequence;
    }

    @Override
    public Object eval() throws Exception {
        while (DataType.cast(condition.eval(), Boolean.class)) {
            sequence.getScope().clear();
            var v = sequence.eval();
            if (v.getClass() == ReturnValue.class) return v;
        }
        return NullObj.get();
    }
    
    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("WHILE <%s> {\n%s\n}", condition.toString(), sequence.toString()));
        return b.toString();
    }
}
