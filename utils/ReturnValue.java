package utils;

import java.util.Arrays;

public class ReturnValue {
    
    public final Object VALUE;

    public ReturnValue(Object value) {
        this.VALUE = value;
    }

    @Override
    public String toString() {
        if (VALUE.getClass() == DataType.ARRAY_CLASS) {
            return Arrays.deepToString((Object[])VALUE);
        }
        return VALUE.toString();
    }
}
