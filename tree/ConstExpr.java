package tree;

import main.TPLProgram;

public class ConstExpr extends Expr {

    protected Object value;

    public ConstExpr(TPLProgram program, Object value) {
        super(program);
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
