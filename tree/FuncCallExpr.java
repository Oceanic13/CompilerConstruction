package tree;

import java.util.Arrays;

import main.TPLProgram;

public class FuncCallExpr extends Expr {
    
    public final String NAME;
    public final Expr[] ARGS;

    public FuncCallExpr(TPLProgram program, String name, Expr...args) {
        super(program);
        this.NAME = name;
        this.ARGS = args;
    }

    @Override
    public Object eval() throws Exception {
        var n = ARGS.length;
        var a = new Object[n];
        for (int i = 0; i<n; ++i) {
            a[i] = ARGS[i].eval();
        }
        return PROGRAM.callFunc(NAME, a);
    }


    @Override
    public String toString() {
        return String.format("%s(%s)", NAME, Arrays.toString(ARGS));
    }
}
