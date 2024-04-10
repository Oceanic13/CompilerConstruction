package main;

public interface ITPLFunction {

    public Object eval(Object[] args) throws Exception;

    public Object eval(TPLScope scope, Object[] args) throws Exception;
    
    public String toString();
}
