package edu.appstate.cs;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class JsonLexerTest {

    @Test
    public void testNextToken() throws Exception {
        String testStr1 = "{}";
        JsonLexer testLex1 = new JsonLexer(new StringReader(testStr1));
        assertEquals(new JsonToken(JsonTokenType.LBRACE), testLex1.nextToken());
        assertEquals(new JsonToken(JsonTokenType.RBRACE), testLex1.nextToken());
        assertEquals(new JsonToken(JsonTokenType.EOF), testLex1.nextToken());

        String testStr2 = "{ \"testkey\" : \"testvalue\" }";
        JsonLexer testLex2 = new JsonLexer(new StringReader(testStr2));
        assertEquals(new JsonToken(JsonTokenType.LBRACE), testLex2.nextToken());
        assertEquals(new JsonToken(JsonTokenType.STR, "testkey"), testLex2.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COLON), testLex2.nextToken());
        assertEquals(new JsonToken(JsonTokenType.STR, "testvalue"), testLex2.nextToken());
        assertEquals(new JsonToken(JsonTokenType.RBRACE), testLex2.nextToken());
        assertEquals(new JsonToken(JsonTokenType.EOF), testLex2.nextToken());

        String testStr3 = "[ \"value1\", \"value2\", \"value3\" ]";
        JsonLexer testLex3 = new JsonLexer(new StringReader(testStr3));
        assertEquals(new JsonToken(JsonTokenType.LBRACKET), testLex3.nextToken());
        assertEquals(new JsonToken(JsonTokenType.STR, "value1"), testLex3.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COMMA), testLex3.nextToken());
        assertEquals(new JsonToken(JsonTokenType.STR, "value2"), testLex3.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COMMA), testLex3.nextToken());
        assertEquals(new JsonToken(JsonTokenType.STR, "value3"), testLex3.nextToken());
        assertEquals(new JsonToken(JsonTokenType.RBRACKET), testLex3.nextToken());
        assertEquals(new JsonToken(JsonTokenType.EOF), testLex3.nextToken());

        String testStr4 = "[ 1, 2, 3, 4 ]";
        JsonLexer testLex4 = new JsonLexer(new StringReader(testStr4));
        assertEquals(new JsonToken(JsonTokenType.LBRACKET), testLex4.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "1"), testLex4.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COMMA), testLex4.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "2"), testLex4.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COMMA), testLex4.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "3"), testLex4.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COMMA), testLex4.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "4"), testLex4.nextToken());
        assertEquals(new JsonToken(JsonTokenType.RBRACKET), testLex4.nextToken());
        assertEquals(new JsonToken(JsonTokenType.EOF), testLex4.nextToken());

        String testStr5 = "[ true, false, 1, 5, 5 ]";
        JsonLexer testLex5 = new JsonLexer(new StringReader(testStr5));
        assertEquals(new JsonToken(JsonTokenType.LBRACKET), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.BOOL, "true"), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COMMA), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.BOOL, "false"), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COMMA), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "1"), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COMMA), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "5"), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.COMMA), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "5"), testLex5.nextToken());
        assertEquals(new JsonToken(JsonTokenType.RBRACKET), testLex5.nextToken());

        String testStr6 = "-0.5e-193 58.238 4782e+3 83.18374E-3 47271.4928494E5938";
        JsonLexer testLex6 = new JsonLexer(new StringReader(testStr6));
        assertEquals(new JsonToken(JsonTokenType.NUM, "-0.5e-193"), testLex6.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "58.238"), testLex6.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "4782e+3"), testLex6.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "83.18374E-3"), testLex6.nextToken());
        assertEquals(new JsonToken(JsonTokenType.NUM, "47271.4928494E5938"), testLex6.nextToken());
    }
}