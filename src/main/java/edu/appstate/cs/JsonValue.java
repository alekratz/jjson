package edu.appstate.cs;

/**
 * Created by alek on 9/19/15.
 */
public abstract class JsonValue<ValueType> {
    protected ValueType value;

    public JsonValue(ValueType value) {
        this.value = value;
    }

    public ValueType getValue() {
        return value;
    }
}
