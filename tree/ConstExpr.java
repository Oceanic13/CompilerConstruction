package tree;

import main.Context;
import structs.DataValue;

public class ConstExpr extends Expr {

    protected DataValue<?> value;

    public ConstExpr(DataValue<?> value) {
        super();
        this.value = value;
    }

    @Override
    public DataValue<?> eval(Context context) {
        return value;
    }
    
}
