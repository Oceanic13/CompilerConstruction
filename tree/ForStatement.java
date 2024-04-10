package tree;

import utils.DataType;
import utils.NullObj;
import utils.ReturnValue;

public class ForStatement extends Statement {

    private Statement initialization;
    private Expr termination;
    private Statement increment;
    private Statement sequence;

    public ForStatement(Statement initialization, Expr termination, Statement increment, Statement sequence) {
        super(initialization.PROGRAM);
        this.initialization = initialization;
        this.termination = termination;
        this.increment = increment;
        this.sequence = sequence;   
    }

    @Override
    public Object eval() throws Exception {
        var v = initialization.eval();
        if (v.getClass() == ReturnValue.class) return v;

        while(DataType.cast(termination.eval(), Boolean.class)) {

            v = sequence.eval();
            if (v.getClass() == ReturnValue.class) return v;

            v = increment.eval();
            if (v.getClass() == ReturnValue.class) return v;
        }

        return NullObj.get();
    }
    
}
