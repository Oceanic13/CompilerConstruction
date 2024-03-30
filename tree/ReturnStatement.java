package tree;

import utils.ReturnValue;

public class ReturnStatement extends Statement {

    private Expr expr;

    public ReturnStatement(Expr expr) {
        super(expr.getScope());
        this.expr = expr;
    }

    @Override
    public Object eval() throws Exception {
        return new ReturnValue(expr.eval());
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append(String.format("RETURN %s", expr.toString()));
        return b.toString();
    }
    
}
