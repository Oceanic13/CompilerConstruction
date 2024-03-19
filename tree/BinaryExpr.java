package tree;

import main.Program;
import scanner.Token;
import utils.DataType;

public class BinaryExpr extends Expr {

    protected Expr left, right;
    public final Token.Type TYPE;

    public BinaryExpr(Token.Type type, Expr left, Expr right) {
        super();
        this.TYPE = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object eval(Program context) {
        return DataType.apply2(TYPE, left.eval(context), right.eval(context));
    }
    
    @Override
    public String toString() {
        return String.format("<%s %s %s>", left, TYPE, right);
    }

    public static class Less extends BinaryExpr {
        public Less(Expr left, Expr right) {
            super(Token.Type.LESS, left, right);
        }
    }
}
