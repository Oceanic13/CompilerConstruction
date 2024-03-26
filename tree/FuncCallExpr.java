package tree;

import java.util.ArrayList;
import java.util.Arrays;

import main.Program;
import main.Scope;
import main.Scope.NullScope;

public class FuncCallExpr extends Expr {
    
    public final String NAME;
    public final Expr[] ARGS;

    public FuncCallExpr(Scope scope, String name, Expr...args) {
        super(scope);
        this.NAME = name;
        this.ARGS = args;
    }

    @Override
    public Object eval() {
        var n = ARGS.length;
        var a = new Object[n];
        for (int i = 0; i<n; ++i) {
            a[i] = ARGS[i].eval();
        }

        // TODO: Handle scope correctly
        return getScope().PROGRAM.callFunc(NAME, a);
    }


    @Override
    public String toString() {
        return String.format("%s(%s)", NAME, Arrays.toString(ARGS));
    }
}
