package edu.appstate.cs;

import java.util.ArrayList;

/**
 * Created by alek on 9/19/15.
 */
public class JsonArray extends JsonValue<ArrayList<JsonValue<?>>> {
    public JsonArray() {
        super(new ArrayList<JsonValue<?>>());
    }

    public void addValue(JsonValue<?> value) {
        this.value.add(value);
    }

    public JsonValue<?> getValue(int index) {
        return this.value.get(index);
    }
}
