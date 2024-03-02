package tree;

public class NullNode implements INode {

    private static NullNode instance;

    private NullNode() {}

    public static NullNode instance() {
        if (instance == null) instance = new NullNode();
        return instance;
    }

    @Override
    public INode parent() {
        return NullNode.instance();
    }

    @Override
    public INode left() {
        return NullNode.instance();
    }

    @Override
    public INode middle() {
        return NullNode.instance();
    }

    @Override
    public INode right() {
        return NullNode.instance();
    }

    @Override
    public int arity() {
        return -1;
    }

    @Override
    public void eval() {
        return;
    }
    
}
