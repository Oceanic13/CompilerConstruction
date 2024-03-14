package tree;

import main.Context;
import utils.DataType;

public class IfStatement extends Statement {

    private Statement[] ifSequence;
    private Expr ifCondition;
    private IfStatement elseIf;

    public IfStatement(Expr ifCondition, Statement[] ifSequence, IfStatement elseIf) {
        super();
        this.ifSequence = ifSequence;
        this.elseIf = elseIf;
        this.ifCondition = ifCondition; 
    }

    @Override
    public void execute(Context context) {
        if (DataType.cast(ifCondition.eval(context), Boolean.class)) {
            for (var s : ifSequence) {
                s.execute(context);
            }
        } else if (elseIf != null && DataType.cast(elseIf.ifCondition.eval(context), Boolean.class)) {
            for (var s : elseIf.ifSequence) {
                s.execute(context);
            }
        }
    }
    
}
