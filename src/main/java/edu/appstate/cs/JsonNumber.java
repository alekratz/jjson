package edu.appstate.cs;

/**
 * Created by alek on 9/19/15.
 */
public class JsonNumber extends JsonValue<Double> {

    public JsonNumber(Double value) {
        super(value);
    }

    public JsonNumber(String value) {
        super(Double.parseDouble(value));
    }
}
