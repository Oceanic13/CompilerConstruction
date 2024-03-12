package tree;

import main.Context;
import structs.DataType;

public class NullExpr extends Expr {

    private static NullExpr instance;

    private NullExpr() {
        super();
    }

    @Override
    public DataType<?> eval(Context context) {
        return null;
    }
    
    public static NullExpr get() {
        if (instance == null) instance = new NullExpr();
        return instance;
    }
}
