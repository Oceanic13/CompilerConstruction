package tree;

import main.Scope;
import utils.DataType;

public class IfStatement extends Statement {

    private Statement ifSequence;
    private Expr ifCondition;
    private Statement elseSequence;

    public IfStatement(Expr ifCondition, Statement ifSequence, Statement elseSequence) {
        super(ifCondition.SCOPE);
        this.ifSequence = ifSequence;
        this.elseSequence = elseSequence;
        this.ifCondition = ifCondition; 
    }

    @Override
    public void execute() {
        if (DataType.cast(ifCondition.eval(), Boolean.class)) {
            ifSequence.execute();
        } else {
            elseSequence.execute();
        }
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("IF <%s> {\n%s\n} ELSE {\n<%s>\n}", ifCondition.toString(), ifSequence.toString(), elseSequence.toString()));
        return b.toString();
    }
}
