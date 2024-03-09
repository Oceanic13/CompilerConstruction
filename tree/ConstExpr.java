package tree;

import utils.Data;

public class ConstExpr extends Expr {

    protected Data value;

    public ConstExpr(Program program, Data value) {
        super(program);
        this.value = value;
    }

    @Override
    public Data eval() {
        return value;
    }
    
    
}
