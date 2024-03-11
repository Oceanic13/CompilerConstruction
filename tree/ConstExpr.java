package tree;

import main.Context;
import utils.Data;

public class ConstExpr extends Expr {

    protected Data value;

    public ConstExpr(Data value) {
        super();
        this.value = value;
    }

    @Override
    public Data eval(Context context) {
        return value;
    }
    
}
