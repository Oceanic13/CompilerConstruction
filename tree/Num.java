package tree;
public class Num implements IExpr {

    private double value;

    public Num(double value) {
        this.value = value;
    }

    @Override
    public double eval() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return "" + this.value;
    }
}
