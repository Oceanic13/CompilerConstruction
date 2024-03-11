package tree;

import main.Context;
import utils.Data;

public abstract class Expr extends Node {

    public Expr() {
    }

    public abstract Data eval(Context context);
}
