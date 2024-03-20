package tree;

import main.Program;
import main.Scope;
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
    public void execute() {
        initialization.execute();

        while(DataType.cast(termination.eval(), Boolean.class)) {

            sequence.execute();

            increment.execute();
        }
    }
    
}
