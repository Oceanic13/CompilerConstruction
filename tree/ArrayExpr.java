package tree;

import java.util.ArrayList;

import main.Scope;

public class ArrayExpr extends Expr {

    private ArrayList<Expr> items;

    public ArrayExpr(Scope scope, ArrayList<Expr> items) {
        super(scope);
        this.items = items;
    }

    @Override
    public Object eval() throws Exception {
        int n = items.size();
        Object[] e = new Object[n];
        for (int i = 0; i < n; ++i) {
            e[i] = items.get(i).eval();
        }
        return e;
    }
    
    public Expr get(int i) {
        return items.get(i);
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
