package tree;

import java.util.function.UnaryOperator;

import main.Context;
import structs.DataValue;

public class UnaryExpr extends Expr {

    protected Expr child;
    protected UnaryOperator<DataValue<?>> func;

    public UnaryExpr(UnaryOperator<DataValue<?>> func, Expr child) {
        super();
        this.func = func;
        this.child = child;
    }

    @Override
    public DataValue<?> eval(Context context) {
        return func.apply(child.eval(context));
    }
    
}
