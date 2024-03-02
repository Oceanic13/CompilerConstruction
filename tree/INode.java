package tree;

public interface INode {
    
    public INode parent();
    public INode left();
    public INode middle();
    public INode right();
    public int arity();
    public void eval();

}
