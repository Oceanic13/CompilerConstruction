package tree;

import java.nio.ByteBuffer;

public class Data {

    public enum DataType {
        NUMBER, BOOLEAN, STRING
    }
    
    private DataType type;
    private byte[] b;

    public Data(double v) {
        this.type = DataType.NUMBER;
        this.b = new byte[8];
        ByteBuffer.wrap(b).putDouble(v);
    }

    public Data(boolean v) {
        this.type = DataType.BOOLEAN;
        this.b = new byte[1];
        this.b[0] = v? (byte)1 : (byte)0;
    }

    public Data(String v) {
        this.type = DataType.STRING;
        this.b = v.getBytes();
    }

    public boolean asBoolean() {
        switch (type) {
            case BOOLEAN: return b[0]!=0;
            case NUMBER: return asNumber()!=0;
            case STRING: return b.length>0;
            default: return false;
        }
    }

    public double asNumber() {
        switch (type) {
            case BOOLEAN: return (b[0]!=0)? 1 : 0;
            case NUMBER: return ByteBuffer.wrap(b).getDouble();
            case STRING: return b.length;
            default: return 0;
        }
    }

    public String asString() {
        switch (type) {
            case BOOLEAN: return (b[0]!=0)? "TRUE" : "FALSE";
            case NUMBER: return "" + asNumber();
            case STRING: return new String(b);
            default: return "NULL";
        }
    }
}
