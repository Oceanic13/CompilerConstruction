package structs;

public class DecimalType extends DataType<Double> {
    

    protected DecimalType(Double value) {
        super(value);
    }

    @Override
    public BooleanType asBool() {
        return new BooleanType(value != 0.);
    }
    @Override
    public IntegerType asInt() {
        return new IntegerType(value.intValue());
    }
    @Override
    public DecimalType asDec() {
        return this;
    }
    @Override
    public CharacterType asChar() {
        return new CharacterType((char)value.intValue());
    }
    @Override
    public StringType asStr() {
        return new StringType(""+value);
    }

    public DecimalType add(IntegerType o) {
        return new DecimalType(value + o.value);
    }

    public DecimalType add(DecimalType o) {
        return new DecimalType(value + o.value);
    }
}
