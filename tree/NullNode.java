package tree;

public class NullNode extends Node {

    private static NullNode instance;

    private NullNode() {
        super(null);
    }

    public static NullNode get() {
        if (instance == null) instance = new NullNode();
        return instance;
    }
}
