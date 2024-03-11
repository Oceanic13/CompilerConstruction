package structs;

public class StringType extends DataType<String> {

    protected StringType(String value) {
        super(value);
    }

    @Override
    public BooleanType asBool() {
        return new BooleanType(value.length() > 0);
    }
    @Override
    public IntegerType asInt() {
        return new IntegerType(value.length());
    }
    @Override
    public DecimalType asDec() {
        return new DecimalType((double)(value.length()));
    }
    @Override
    public CharacterType asChar() {
        return new CharacterType((value.length()>0)? value.charAt(0) : '\0');
    }
    @Override
    public StringType asStr() {
        return this;
    }

    public StringType add(StringType o) {
        return new StringType(value + o.value);
    }

    public StringType sub(CharacterType o) {
        return new StringType(value.replaceAll(""+o.value, ""));
    }

    public StringType mult(IntegerType o) {
        StringBuilder b = new StringBuilder();
        int n = o.value;
        b.repeat(value, Math.abs(n));
        if (n < 0) {b.reverse();}
        return new StringType(b.toString());
    }
}
