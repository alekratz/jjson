package edu.appstate.cs;

/**
 * Created by alek on 9/21/15.
 */
public class JsonBool extends JsonValue<Boolean> {

    public JsonBool(Boolean value) {
        super(value);
    }

    public JsonBool(String value) {
        super(Boolean.parseBoolean(value));
    }
}
