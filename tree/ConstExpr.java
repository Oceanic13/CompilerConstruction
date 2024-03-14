package tree;

import main.Context;

public class ConstExpr extends Expr {

    protected Object value;

    public ConstExpr(Object value) {
        super();
        this.value = value;
    }

    @Override
    public Object eval(Context context) {
        return value;
    }
    
}
