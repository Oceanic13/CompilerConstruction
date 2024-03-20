package tree;

import main.Program;
import main.Scope;
import scanner.Token;
import utils.DataType;

public class UnaryExpr extends Expr {

    protected Expr child;
    protected Token.Type type;

    public UnaryExpr(Token.Type type, Expr child) {
        super();
        this.type = type;
        this.child = child;
    }

    @Override
    public Object eval() {
        return DataType.apply1(type, child.eval());
    }
    
    @Override
    public String toString() {
        return String.format("<%s %s>", type, child);
    }
}
