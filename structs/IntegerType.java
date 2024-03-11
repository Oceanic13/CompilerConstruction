package structs;

public class IntegerType extends DataType<Integer> {
    
    protected IntegerType(Integer value) {
        super(value);
    }

    @Override
    public BooleanType asBool() {
        return new BooleanType(value != 0);
    }
    @Override
    public IntegerType asInt() {
        return this;
    }
    @Override
    public DecimalType asDec() {
        return new DecimalType(value.doubleValue());
    }
    @Override
    public CharacterType asChar() {
        return new CharacterType((char)value.intValue());
    }
    @Override
    public StringType asStr() {
        return new StringType(""+value);
    }

    public IntegerType add(IntegerType o) {
        return new IntegerType(value + o.value);
    }

    public DecimalType add(DecimalType o) {
        return new DecimalType(value + o.value);
    }
}
