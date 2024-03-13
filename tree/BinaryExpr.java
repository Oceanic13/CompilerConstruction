package tree;

import java.util.function.BinaryOperator;

import main.Context;
import structs.DataValue;

public class BinaryExpr extends Expr {

    protected Expr left, right;
    protected BinaryOperator<DataValue<?>> func;

    protected BinaryExpr(BinaryOperator<DataValue<?>> func, Expr left, Expr right) {
        super();
        this.func = func;
        this.left = left;
        this.right = right;
    }

    @Override
    public DataValue<?> eval(Context context) {
        return func.apply(left.eval(context), right.eval(context));
    }
    
}
