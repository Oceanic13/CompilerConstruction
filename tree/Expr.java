package tree;

import main.Context;
import structs.DataValue;

public abstract class Expr extends Statement {

    public Expr() {
    }

    public abstract DataValue<?> eval(Context context);

    @Override
    public void execute(Context context) {
        // Do nothing
    }
}
