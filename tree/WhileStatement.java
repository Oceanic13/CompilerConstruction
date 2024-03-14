package tree;

import main.Context;
import utils.DataType;

public class WhileStatement extends Statement {

    private Expr condition;
    private Statement[] sequence;

    public WhileStatement(Expr condition, Statement... sequence) {
        super();
        this.condition = condition;
        this.sequence = sequence;
    }

    @Override
    public void execute(Context context) {
        while (DataType.cast(condition.eval(context), Boolean.class)) {
            for (var s : sequence) {
                s.execute(context);
            }
        }
    }
    
}
