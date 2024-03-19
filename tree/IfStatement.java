package tree;

import main.Program;
import utils.DataType;

public class IfStatement extends Statement {

    private Statement ifSequence;
    private Expr ifCondition;
    private Statement elseSequence;

    public IfStatement(Expr ifCondition, Statement ifSequence, Statement elseSequence) {
        super();
        this.ifSequence = ifSequence;
        this.elseSequence = elseSequence;
        this.ifCondition = ifCondition; 
    }

    @Override
    public void execute(Program context) {
        if (DataType.cast(ifCondition.eval(context), Boolean.class)) {
            ifSequence.execute(context);
        } else {
            elseSequence.execute(context);
        }
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("IF <%s> {\n%s\n} ELSE {\n<%s>\n}", ifCondition.toString(), ifSequence.toString(), elseSequence.toString()));
        return b.toString();
    }
}
