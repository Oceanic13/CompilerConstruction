package tree;

public class While extends Node {

    public While(INode parent, INode left, INode right) {
        super(parent, left, right);
    }

    @Override
    public Data eval() {
        while (left().eval().asBoolean()) {
            right().eval();
        }
        return null;
    }
    
}
