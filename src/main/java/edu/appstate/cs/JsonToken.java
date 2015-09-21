package edu.appstate.cs;

/**
 * Created by alek on 9/17/15.
 */
public class JsonToken {

    private JsonTokenType type;
    private String contents;

    public JsonToken(JsonTokenType type) {
        this.type = type;
        this.contents = "";
    }

    public JsonToken(JsonTokenType type, String contents) {
        this.type = type;
        this.contents = contents;
    }

    public JsonTokenType getType() {
        return type;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        return ((JsonToken)o).type == type && ((JsonToken)o).contents.equals(contents);
    }
}
