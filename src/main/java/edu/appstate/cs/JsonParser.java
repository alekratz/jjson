package edu.appstate.cs;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import static edu.appstate.cs.JsonTokenType.*;

/**
 * Created by alek on 9/19/15.
 */
public class JsonParser {
    private JsonLexer lexer;
    private JsonToken currTok;
    private ArrayList<JsonValue<?>> values = new ArrayList<>();

    public JsonParser(JsonLexer lexer) {
        this.lexer = lexer;
    }

    public JsonParser(Reader reader) {
        this.lexer = new JsonLexer(reader);
    }

    public JsonParser(String str) {
        this.lexer = new JsonLexer(new StringReader(str));
    }

    public void parse() {
        next();
        while(currTok.getType() != EOF) {
            switch (currTok.getType()) {
                case LBRACE:
                    values.add(object());
                    break;
                case LBRACKET:
                    values.add(array());
                    break;
                default:
                    error(LBRACE, LBRACKET);
                    break;
            }
        }
    }

    public JsonValue<?>[] getValues() {
        JsonValue<?>[] copy = new JsonValue<?>[values.size()];
        values.toArray(copy);
        return copy;
    }

    private JsonValue<?> object() {
        JsonObject object = new JsonObject();
        match(LBRACE);
        objectList(object);
        match(RBRACE);
        return object;
    }

    private void objectList(JsonObject object) {
        if(currTok.getType() == STR) {
            String key = currTok.getContents();
            match(STR);
            match(COLON);
            JsonValue<?> val = value();
            object.addValue(key, val);
            objectListTail(object);
        } else if(currTok.getType() != RBRACE) {
            error(STR, RBRACE);
        }
    }

    private void objectListTail(JsonObject object) {
        if(currTok.getType() == COMMA) {
            match(COMMA);
            String key = currTok.getContents();
            match(STR);
            match(COLON);
            JsonValue<?> val = value();
            object.addValue(key, val);
            objectListTail(object);
        } else if(currTok.getType() != RBRACE) {
            error(COMMA, RBRACE);
        }
    }

    private JsonArray array() {
        JsonArray array = new JsonArray();
        match(LBRACKET);
        arrayList(array);
        match(RBRACKET);
        return array;
    }

    private void arrayList(JsonArray array) {
        if(currTok.getType() == STR || currTok.getType() == NUM || currTok.getType() == LBRACE ||
                currTok.getType() == LBRACKET || currTok.getType() == BOOL || currTok.getType() == NULL) {
            array.addValue(value());
            arrayListTail(array);
        } else if(currTok.getType() != RBRACKET) {
            error(STR, NUM, LBRACE, LBRACKET, BOOL, NULL,
                    RBRACKET);
        }
    }

    private void arrayListTail(JsonArray array) {
        if(currTok.getType() == COMMA) {
            array.addValue(value());
            arrayListTail(array);
        } else if(currTok.getType() != RBRACKET) {
            error(COMMA, RBRACKET);
        }
    }

    private JsonValue<?> value() {
        switch(currTok.getType()) {
            case STR:
                JsonStr str = new JsonStr(currTok.getContents());
                match(STR);
                return str;
            case NUM:
                JsonNumber num = new JsonNumber(currTok.getContents());
                match(NUM);
                return num;
            case LBRACE:
                return object();
            case LBRACKET:
                return array();
            case BOOL:
                JsonBool bool = new JsonBool(currTok.getContents());
                match(BOOL);
                return bool;
            case NULL:
                match(NULL);
                return null;
            default:
                error(STR, NUM, LBRACE, LBRACKET, BOOL, NULL);
                break;
        }
        assert false;
        return null;
    }

    private void match(JsonTokenType tokenType) {
        if(currTok.getType() != tokenType) {
            error(tokenType);
        }
        next();
        // HACK: this is just how the lexer works, this should be fixed
        lexer.skipWhitespace();
    }

    private void next() {
        currTok = lexer.nextToken();
    }

    private void error(JsonTokenType expected) {
        error("expected token " + expected + " but instead got " + currTok.getType());
    }

    private void error(JsonTokenType... allExpected) {
        String errStr = "expected token ";
        for(int i = 0; i < allExpected.length; i++) {
            if(i == allExpected.length - 1) {
                errStr += allExpected[i] + " ";
            } else {
                errStr += allExpected[i] + ", or ";
            }
        }
        errStr += "but instead got " + currTok.getType();
        error(errStr);
    }

    private void error(String message) {
        throw new RuntimeException("PARSER: Error on line " + lexer.getLineIndex() + ":" + lexer.getColIndex() + ", source index "  + lexer.getSourceIndex() +
                ": " + message);
    }
}
