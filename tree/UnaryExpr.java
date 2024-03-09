package tree;

import java.util.function.UnaryOperator;

import utils.Data;

public class UnaryExpr extends Expr {

    protected Expr child;
    protected UnaryOperator<Data> func;

    public UnaryExpr(Program program, UnaryOperator<Data> func, Expr child) {
        super(program);
        this.func = func;
        this.child = child;
    }

    @Override
    public Data eval() {
        return func.apply(child.eval());
    }
    
}
