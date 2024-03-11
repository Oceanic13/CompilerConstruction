package tree;

import java.util.function.BinaryOperator;

import main.Context;
import structs.DataType;

public class BinaryExpr extends Expr {

    protected Expr left, right;
    protected BinaryOperator<DataType> func;

    protected BinaryExpr(BinaryOperator<DataType> func, Expr left, Expr right) {
        super();
        this.func = func;
        this.left = left;
        this.right = right;
    }

    @Override
    public DataType eval(Context context) {
        return func.apply(left.eval(context), right.eval(context));
    }
    
}
