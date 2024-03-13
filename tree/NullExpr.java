package tree;

import main.Context;
import structs.DataValue;

public class NullExpr extends Expr {

    private static NullExpr instance;

    private NullExpr() {
        super();
    }

    @Override
    public DataValue<?> eval(Context context) {
        return null;
    }
    
    public static NullExpr get() {
        if (instance == null) instance = new NullExpr();
        return instance;
    }
}
