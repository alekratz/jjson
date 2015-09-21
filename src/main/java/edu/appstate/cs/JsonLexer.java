package edu.appstate.cs;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by alek on 9/17/15.
 */
public class JsonLexer {

    private boolean reachedEOF = false;
    private Reader reader;
    private int currChar;
    private int sourceIndex;
    private int lineIndex;
    private int colIndex;

    public JsonLexer(Reader reader) {
        this.reader = reader;
        currChar = 0;
        sourceIndex = -1;
        lineIndex = 1;
        colIndex = -1;
        next();
    }

    public JsonToken nextToken() {
        skipWhitespace();

        if(reachedEOF) {
            // eof
            return new JsonToken(JsonTokenType.EOF);
        } else if(currChar == -1) {
            // error
            return new JsonToken(JsonTokenType.ERROR);
        } else if(currChar == '"') {
            // string
            String strValue = "";
            next();
            while(currChar != '"') {
                if(reachedEOF)
                    error("unexpected EOF reached before end of string");
                assert(currChar != -1);
                if(currChar == '\\') {
                    // escape sequence
                    next();
                    switch(currChar) {
                        case '\\':
                            strValue += '\\';
                            break;
                        case 'n':
                            strValue += '\n';
                            break;
                        case 'r':
                            strValue += '\r';
                            break;
                        case 't':
                            strValue += '\t';
                            break;
                        case '\'':
                            strValue += '\'';
                            break;
                        case '/':
                            strValue += '/';
                            break;
                        case 'b':
                            strValue += '\b';
                            break;
                        case 'f':
                            strValue += '\f';
                            break;
                        // TODO : unicode
                        case '"':
                            strValue += '"';
                            break;
                        default:
                            error("unrecognized escape character: " + (char)currChar);
                            break;
                    }
                } else {
                    strValue += (char) currChar;
                }
                next();
            }
            next();
            return new JsonToken(JsonTokenType.STR, strValue);
        } else if(currChar == 't') {
            matchStr("true");
            return new JsonToken(JsonTokenType.BOOL, "true");
        } else if(currChar == 'f') {
            matchStr("false");
            return new JsonToken(JsonTokenType.BOOL, "false");
        } else if(currChar == 'n') {
            matchStr("null");
            return new JsonToken(JsonTokenType.NULL, "null");
        } else if(Character.isDigit(currChar) || currChar == '-') {
            String numStr = "";

            if(currChar == '-') {
                numStr += (char)currChar;
                next();
            }

            if(currChar == '0') {
                numStr += (char)currChar;
                match('0');
            } else {
                // ensure digit as first character
                if(!Character.isDigit(currChar))
                    error("expected digit, but instead got " + (char)currChar);

                do {
                    numStr += (char) currChar;
                    next();
                } while (Character.isDigit(currChar));
            }

            if(currChar == '.') {
                numStr += (char)currChar;
                match('.');
                // ensure digit after decimal
                if(!Character.isDigit(currChar))
                    error("expected digit, but instead got " + (char)currChar);

                do {
                    numStr += (char) currChar;
                    next();
                } while (Character.isDigit(currChar));
            }

            if(currChar == 'e' || currChar == 'E') {
                numStr += (char)currChar;
                next();

                if(currChar == '+' || currChar == '-') {
                    numStr += (char)currChar;
                    next();
                }

                // ensure digit after scientific notation
                if(!Character.isDigit(currChar))
                    error("expected digit, but instead got " + (char)currChar);

                do {
                    numStr += (char) currChar;
                    next();
                } while (Character.isDigit(currChar));
            }

            return new JsonToken(JsonTokenType.NUM, numStr);
        } else if(currChar == '{') {
            match('{');
            return new JsonToken(JsonTokenType.LBRACE);
        } else if(currChar == '}') {
            match('}');
            return new JsonToken(JsonTokenType.RBRACE);
        } else if(currChar == '[') {
            match('[');
            return new JsonToken(JsonTokenType.LBRACKET);
        } else if(currChar == ']') {
            match(']');
            return new JsonToken(JsonTokenType.RBRACKET);
        } else if(currChar == ':') {
            match(':');
            return new JsonToken(JsonTokenType.COLON);
        } else if(currChar == ',') {
            match(',');
            return new JsonToken(JsonTokenType.COMMA);
        }

        return null;
    }

    public void skipWhitespace() {
        while(Character.isWhitespace(currChar))
            next();
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public int getSourceIndex() {
        return sourceIndex;
    }

    public int getCurrChar() {
        return currChar;
    }

    private void matchStr(String str) {
        try {
            str.chars().forEachOrdered(c -> match((char) c));
        } catch(RuntimeException ex) {
            error("unexpected identifier");
        }
    }

    private void match(char c) {
        if(c != currChar)
            error(c);
        next();
    }

    private void next() {
        if(reachedEOF) {
            return;
        }

        try {
            currChar = reader.read();
            if(currChar == -1) {
                reachedEOF = true;
            } else if(currChar == '\n') {
                lineIndex++;
                colIndex = -1;
            }
            colIndex++;
            sourceIndex++;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void error(char expected) {
        error("unexpected character: " + (char)currChar + ", expected " + expected);
    }
/*
    private void error() {
        error("unexpected character: " + (char)currChar);
    }
*/
    private void error(String message) {
        throw new RuntimeException("LEXER: Error on line " + lineIndex + ":" + colIndex + ", source index"  + sourceIndex +
            ": " + message);
    }
}
