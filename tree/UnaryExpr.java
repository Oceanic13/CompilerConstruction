package tree;

import java.util.function.UnaryOperator;

import main.Context;
import structs.DataType;

public class UnaryExpr extends Expr {

    protected Expr child;
    protected UnaryOperator<DataType> func;

    public UnaryExpr(UnaryOperator<DataType> func, Expr child) {
        super();
        this.func = func;
        this.child = child;
    }

    @Override
    public DataType eval(Context context) {
        return func.apply(child.eval(context));
    }
    
}
