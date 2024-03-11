package structs;

public abstract class DataType<T> {

    protected T value;

    protected DataType(T value) {
        this.value = value;
    }

    public abstract BooleanType asBool();
    public abstract IntegerType asInt();
    public abstract DecimalType asDec();
    public abstract CharacterType asChar();
    public abstract StringType asStr();

    public T value() {
        return value;
    }
}
