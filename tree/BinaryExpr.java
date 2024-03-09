package tree;

import java.util.function.BinaryOperator;

import utils.Data;

public class BinaryExpr extends Expr {

    protected Expr left, right;
    protected BinaryOperator<Data> func;

    public BinaryExpr(Program program, BinaryOperator<Data> func, Expr left, Expr right) {
        super(program);
        this.func = func;
        this.left = left;
        this.right = right;
    }

    @Override
    public Data eval() {
        return func.apply(left.eval(), right.eval());
    }

    
}
