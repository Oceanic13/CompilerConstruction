package tree;

import main.Program;
import main.Scope;

public class ReturnStatement extends Statement {

    private Expr expr;

    public ReturnStatement(Expr expr) {
        super();
        this.expr = expr;
    }

    @Override
    public void execute() {
        expr.eval(); //TODO: Return this value
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("RETURN %s", expr.toString()));
        return b.toString();
    }
    
}
