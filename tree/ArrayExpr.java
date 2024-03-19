package tree;

import java.util.ArrayList;

import main.Program;

public class ArrayExpr extends Expr {

    private ArrayList<Expr> items;

    public ArrayExpr(ArrayList<Expr> items) {
        this.items = items;
    }

    @Override
    public Object eval(Program context) {
        int n = items.size();
        Object[] e = new Object[n];
        for (int i = 0; i < n; ++i) {
            e[i] = items.get(i).eval(context);
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
