package tree;

import main.Context;
import structs.DataType;

public abstract class Expr extends Statement {

    public Expr() {
    }

    public abstract DataType<?> eval(Context context);

    @Override
    public void execute(Context context) {
        // Do nothing
    }
}
