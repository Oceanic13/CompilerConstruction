package utils;

public class ReturnValue {
    
    public final Object VALUE;

    public ReturnValue(Object value) {
        this.VALUE = value;
    }

    @Override
    public String toString() {
        return VALUE.toString();
    }
}
