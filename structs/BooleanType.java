package structs;

public class BooleanType extends DataType<Boolean> {

    protected BooleanType(Boolean value) {
        super(value);
    }
    
    @Override
    public BooleanType asBool() {
        return this;
    }
    @Override
    public IntegerType asInt() {
        return new IntegerType(value? 1 : 0);
    }
    @Override
    public DecimalType asDec() {
        return new DecimalType(value? 1. : 0.);
    }
    @Override
    public CharacterType asChar() {
        return new CharacterType(value? '1' : '0');
    }
    @Override
    public StringType asStr() {
        return new StringType(value? "true" : "false");
    }

    public BooleanType not() {
        return new BooleanType(!value);
    }

    public BooleanType or(BooleanType o) {
        return new BooleanType(value || o.value);
    }

    public BooleanType and(BooleanType o) {
        return new BooleanType(value && o.value);
    }


}
