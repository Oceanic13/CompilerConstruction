package tree;

import main.Context;

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
        while (condition.eval(context).cast(Boolean.class).value()) {
            for (var s : sequence) {
                s.execute(context);
            }
        }
    }
    
}
