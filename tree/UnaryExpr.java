package tree;

import java.util.function.UnaryOperator;

import main.Context;
import utils.Data;

public class UnaryExpr extends Expr {

    protected Expr child;
    protected UnaryOperator<Data> func;

    public UnaryExpr(UnaryOperator<Data> func, Expr child) {
        super();
        this.func = func;
        this.child = child;
    }

    @Override
    public Data eval(Context context) {
        return func.apply(child.eval(context));
    }
    
}
