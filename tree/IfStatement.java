package tree;

import main.Scope;
import utils.DataType;
import utils.NullObj;
import utils.ReturnValue;

public class IfStatement extends Statement {

    private Statement ifSequence;
    private Expr ifCondition;
    private Statement elseSequence;

    public IfStatement(Expr ifCondition, Statement ifSequence, Statement elseSequence) {
        super(ifCondition.getScope());
        this.ifSequence = ifSequence;
        this.elseSequence = elseSequence;
        this.ifCondition = ifCondition; 
    }

    @Override
    public Object eval() throws Exception {
        if (DataType.cast(ifCondition.eval(), Boolean.class)) {
            var v = ifSequence.eval();
            if (v.getClass() == ReturnValue.class) return v;
        } else {
            var v = elseSequence.eval();
            if (v.getClass() == ReturnValue.class) return v;
        }
        return NullObj.get();
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("IF <%s> {\n%s\n} ELSE {\n<%s>\n}", ifCondition.toString(), ifSequence.toString(), elseSequence.toString()));
        return b.toString();
    }
}
