package edu.appstate.cs;

import java.util.HashMap;

/**
 * Created by alek on 9/19/15.
 */
public class JsonObject extends JsonValue<HashMap<String, JsonValue<?>>> {

    public JsonObject() {
        super(new HashMap<String, JsonValue<?>>());
    }

    public void addValue(String key, JsonValue<?> value) {
        this.value.put(key, value);
    }

    public JsonValue<?> getValue(String key) {
        return this.value.get(key);
    }
}
