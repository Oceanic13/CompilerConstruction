package tree;

import main.Context;
import structs.DataType;

public abstract class Expr extends Node {

    public Expr() {
    }

    public abstract DataType<?> eval(Context context);
}
