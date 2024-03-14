package tree;

import main.Program;
import utils.DataType;

public class PrintStatement extends Statement {

    private Expr expr;

    public PrintStatement(Expr expr) {
        super();
        this.expr = expr;
    }

    @Override
    public void execute(Program context) {
        System.out.println(DataType.cast(expr.eval(context), String.class));
    }
}
