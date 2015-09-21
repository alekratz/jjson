package edu.appstate.cs;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonParserTest {

    @Test
    public void testParse() throws Exception {
        String str1 = "{}";
        JsonParser p1 = new JsonParser(str1);
        p1.parse();
        JsonObject obj1 = (JsonObject)p1.getValues()[0];
        assertEquals(1, p1.getValues().length);
        assertEquals(0, obj1.getValue().size());

        String str2 = "[]";
        JsonParser p2 = new JsonParser(str2);
        p2.parse();
        JsonArray ary2 = (JsonArray)p2.getValues()[0];
        assertEquals(1, p2.getValues().length);
        assertEquals(0, ary2.getValue().size());

        String str3 = "{\"a\" : \"some value\"}";
        JsonParser p3 = new JsonParser(str3);
        p3.parse();
        JsonObject obj3 = (JsonObject)p3.getValues()[0];
        assertEquals(1, p3.getValues().length);
        assertEquals(1, obj3.getValue().size());
        assertEquals("some value", ((JsonStr)obj3.getValue("a")).getValue());

        String str4 = "{\"a\" : \"some value\", \"b\" : true}";
        JsonParser p4 = new JsonParser(str4);
        p4.parse();
        JsonObject obj4 = (JsonObject)p4.getValues()[0];
        assertEquals(1, p4.getValues().length);
        assertEquals(2, obj4.getValue().size());
        assertEquals("some value", ((JsonStr)obj4.getValue("a")).getValue());
        assertEquals(true, ((JsonBool)obj4.getValue("b")).getValue());

        String str5 = "{\"a\" : \"some value\", \"b\" : true, \"c\" : 1.2}";
        JsonParser p5 = new JsonParser(str5);
        p5.parse();
        JsonObject obj5 = (JsonObject)p5.getValues()[0];
        assertEquals(1, p5.getValues().length);
        assertEquals(3, obj5.getValue().size());
        assertEquals("some value", ((JsonStr)obj5.getValue("a")).getValue());
        assertEquals(true, ((JsonBool)obj5.getValue("b")).getValue());
        assertEquals(Double.valueOf(1.2), ((JsonNumber)obj5.getValue("c")).getValue());

        String str6 = "{\"a\" : \"some value\", \"b\" : true, \"c\" : 1.2, \"d\" : null}";
        JsonParser p6 = new JsonParser(str6);
        p6.parse();
        JsonObject obj6 = (JsonObject)p6.getValues()[0];
        assertEquals(1, p6.getValues().length);
        assertEquals(4, obj6.getValue().size());
        assertEquals("some value", ((JsonStr)obj6.getValue("a")).getValue());
        assertEquals(true, ((JsonBool)obj6.getValue("b")).getValue());
        assertEquals(Double.valueOf(1.2), ((JsonNumber)obj6.getValue("c")).getValue());
        assertEquals(null, obj6.getValue("d"));
    }
}