package tree;

import main.Program;
import utils.DataType;

public class IfStatement extends Statement {

    private MultiStatement ifSequence;
    private Expr ifCondition;
    private IfStatement elseIf;

    public IfStatement(Expr ifCondition, Statement[] ifSequence, IfStatement elseIf) {
        super();
        this.ifSequence = new MultiStatement(ifSequence);
        this.elseIf = elseIf;
        this.ifCondition = ifCondition; 
    }

    @Override
    public void execute(Program context) {
        if (DataType.cast(ifCondition.eval(context), Boolean.class)) {
            ifSequence.execute(context);
        } else if (elseIf != null && DataType.cast(elseIf.ifCondition.eval(context), Boolean.class)) {
            elseIf.ifSequence.execute(context);
        }
    }
}
