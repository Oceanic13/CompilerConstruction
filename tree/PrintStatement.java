package tree;

import utils.DataType;
import utils.NullObj;

public class PrintStatement extends Statement {

    private Expr expr;

    public PrintStatement(Expr expr) {
        super(expr.getScope());
        this.expr = expr;
    }

    @Override
    public Object eval() {
        String s = DataType.cast(expr.eval(), String.class);
        System.out.println(s);
        return NullObj.get();
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("PRINT %s", expr.toString()));
        return b.toString();
    }
}
