package tree;

import main.Program;

public class ReturnStatement extends Statement {

    private Expr expr;

    public ReturnStatement(Expr expr) {
        super();
        this.expr = expr;
    }

    @Override
    public void execute(Program context) {
        expr.eval(context); //TODO: Return this value
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("RETURN %s", expr.toString()));
        return b.toString();
    }
    
}
