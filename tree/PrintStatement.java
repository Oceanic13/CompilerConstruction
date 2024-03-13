package tree;

import main.Context;

public class PrintStatement extends Statement {

    private Expr expr;

    public PrintStatement(Expr expr) {
        super();
        this.expr = expr;
    }

    @Override
    public void execute(Context context) {
        System.out.println(expr.eval(context).cast(String.class).value());
    }
}
