package tree;

import main.Program;

public class ConstExpr extends Expr {

    protected Object value;

    public ConstExpr(Object value) {
        super();
        this.value = value;
    }

    @Override
    public Object eval() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.format("<%s>", value);
    }
}
