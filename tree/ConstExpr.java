package tree;

import main.Context;
import structs.DataType;

public class ConstExpr extends Expr {

    protected DataType<?> value;

    public ConstExpr(DataType<?> value) {
        super();
        this.value = value;
    }

    @Override
    public DataType<?> eval(Context context) {
        return value;
    }
    
}
