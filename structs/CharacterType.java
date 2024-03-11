package structs;

public class CharacterType extends DataType<Character> {

    protected CharacterType(Character value) {
        super(value);
    }

    @Override
    public BooleanType asBool() {
        return new BooleanType(value != '0');
    }
    @Override
    public IntegerType asInt() {
        return new IntegerType((int)value);
    }
    @Override
    public DecimalType asDec() {
        return new DecimalType((double)value);
    }
    @Override
    public CharacterType asChar() {
        return this;
    }
    @Override
    public StringType asStr() {
        return new StringType(""+value);
    }

    public StringType add(CharacterType o) {
        return new StringType("" + value + o.value);
    }

    public StringType mult(IntegerType o) {
        return new StringType(new StringBuilder().repeat(value, Math.abs(o.value)).toString());
    }
}