package tree;

import utils.Data;

public abstract class Expr extends Node {

    public Expr(Program program) {
        super(program);
    }

    public abstract Data eval();
}
