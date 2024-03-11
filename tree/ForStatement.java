package tree;

import main.Context;

public class ForStatement extends Statement {

    private Statement initialization;
    private Expr termination;
    private Statement increment;

    public ForStatement(Statement initialization, Expr termination, Statement increment) {
        super();
        this.initialization = initialization;
        this.termination = termination;
        this.increment = increment;   
    }

    @Override
    public void execute(Context context) {
        initialization.execute(context);
        while(!termination.eval(context).asBool()) {
            increment.execute(context);
        }
    }
    
}
