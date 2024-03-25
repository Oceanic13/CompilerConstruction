package tree;

import utils.DataType;

public class PrintStatement extends Statement {

    private Expr expr;

    public PrintStatement(Expr expr) {
        super(expr.SCOPE);
        this.expr = expr;
    }

    @Override
    public void execute() {
        System.out.println(DataType.cast(expr.eval(), String.class));
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("PRINT %s", expr.toString()));
        return b.toString();
    }
}
