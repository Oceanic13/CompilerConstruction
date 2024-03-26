package tree;

import main.Scope;

public class VarExpr extends Expr {

    public final String NAME;

    public VarExpr(Scope scope, String name) {
        super(scope);
        this.NAME = name;
    }

    @Override
    public Object eval() {
        return getScope().readVar(NAME);
    }
    
    @Override
    public String toString() {
        return String.format("<VAR:%s>", NAME);
    }
}
