package tree;
public class Num implements IExpr {

    private int value;

    public Num(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return "" + this.value;
    }
}
