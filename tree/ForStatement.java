package tree;

import main.Program;
import utils.DataType;

public class ForStatement extends Statement {

    private Statement initialization;
    private Expr termination;
    private Statement increment;
    private Statement sequence;

    public ForStatement(Statement initialization, Expr termination, Statement increment, Statement sequence) {
        super();
        this.initialization = initialization;
        this.termination = termination;
        this.increment = increment;
        this.sequence = sequence;   
    }

    @Override
    public void execute(Program context) {
        initialization.execute(context);

        while(DataType.cast(termination.eval(context), Boolean.class)) {

            sequence.execute(context);

            increment.execute(context);
        }
    }
    
}
