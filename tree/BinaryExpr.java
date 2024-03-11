package tree;

import java.util.function.BinaryOperator;

import main.Context;
import utils.Data;

public class BinaryExpr extends Expr {

    protected Expr left, right;
    protected BinaryOperator<Data> func;

    protected BinaryExpr(BinaryOperator<Data> func, Expr left, Expr right) {
        super();
        this.func = func;
        this.left = left;
        this.right = right;
    }

    @Override
    public Data eval(Context context) {
        return func.apply(left.eval(context), right.eval(context));
    }
    
}
