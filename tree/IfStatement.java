package tree;

import main.Context;

public class IfStatement extends Statement {

    private Statement[] ifSequence, elseSequence;
    private Expr ifCondition, elseCondition;

    public IfStatement(Expr ifCondition, Statement[] ifSequence, Expr elseCondition, Statement[] elseSequence) {
        super();
        this.ifSequence = ifSequence;
        this.elseCondition = elseCondition;
        this.elseSequence = elseSequence;
        this.ifCondition = ifCondition;
    }

    @Override
    public void execute(Context context) {
        if (ifCondition.eval(context).asBool()) {
            for (var s : ifSequence) {
                s.execute(context);
            }
        } else if (elseCondition.eval(context).asBool()) {
            for (var s : elseSequence) {
                s.execute(context);
            }
        }
    }
    
}
