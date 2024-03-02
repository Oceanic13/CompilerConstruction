package tree;

public abstract class Node implements INode {

    protected INode parent;
    protected INode[] children;

    public Node(INode parent, INode...children) {
        this.parent = parent;
        this.children = children;
    }

    @Override
    public INode parent() {
        return parent;
    }

    @Override
    public INode left() {
        assert(arity()>=1);
        return children[0];
    }

    @Override
    public INode middle() {
        assert(arity()==3);
        return children[(int)(arity()/2)];
    }

    @Override
    public INode right() {
        assert(arity()>=2);
        return children[arity()-1];
    }

    @Override
    public int arity() {
        return children.length;
    }
    
}
