package edu.appstate.cs;

/**
 * Created by alek on 9/19/15.
 */
public class JsonStr extends JsonValue<String> {

    public JsonStr(String value) {
        super(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
