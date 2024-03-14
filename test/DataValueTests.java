package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import scanner.Token;
import utils.DataType;

public class DataValueTests {

    @Test
    public void testNullValue() {
        DataType.init();
        assertNull(null);
    }

    @Test
    public void testBoolCasts() {
        DataType.init();

        var b = true;
        assertEquals(true, b);
        assertEquals(1, DataType.cast(b, Integer.class).intValue());
        assertEquals(1., DataType.cast(b, Double.class), 0.);
        assertEquals('1', DataType.cast(b, Character.class).charValue());
        assertEquals("true", DataType.cast(b, String.class));
    }

    @Test
    public void testIntCasts() {
        DataType.init();

        var i = 65;
        assertEquals(true, DataType.cast(i, Boolean.class));
        assertEquals(65, i);
        assertEquals(65., DataType.cast(i, Double.class), 0.);
        assertEquals('A', DataType.cast(i, Character.class).charValue());
        assertEquals("65", DataType.cast(i, String.class));
    }

    @Test
    public void testDecCasts() {
        DataType.init();

        var d = 69.6;
        assertEquals(true, DataType.cast(d, Boolean.class));
        assertEquals(69, DataType.cast(d, Integer.class).intValue());
        assertEquals(69.6, d, 0.);
        assertEquals('E', DataType.cast(d, Character.class).charValue());
        assertEquals("69.6", DataType.cast(d, String.class));
    }

    @Test
    public void testCharCasts() {
        DataType.init();

        var c = 'v';
        assertEquals(true, DataType.cast(c, Boolean.class));
        assertEquals(118, DataType.cast(c, Integer.class).intValue());
        assertEquals(118., DataType.cast(c, Double.class).doubleValue(), 0.0);
        assertEquals('v', c);
        assertEquals("v", DataType.cast(c, String.class));
    }

    @Test
    public void testStrCasts() {
        DataType.init();

        var s = "Hello World";
        assertEquals(true, DataType.cast(s, Boolean.class));
        assertEquals(11, DataType.cast(s, Integer.class).intValue());
        assertEquals(11., DataType.cast(s, Double.class).doubleValue(), 0.0);
        //assertEquals('H', s.cast(Character.class).value().charValue());
        assertEquals("Hello World", s);
    }

    @Test
    public void testNumMinusNum() {
        DataType.init();
        assertEquals(4.5, DataType.apply2(Token.Type.SUB, 10, 5.5), 0.0);
        assertEquals(-4.5, DataType.apply2(Token.Type.SUB, 5.5, 10), 0.0);
    }

    @Test
    public void testStringMinusInt() {
        DataType.init();
        var s = "Hello World";
        assertEquals("Hello World", DataType.apply2(Token.Type.SUB, s, 0));
        assertEquals("Hello Worl", DataType.apply2(Token.Type.SUB, s, 1));
        assertEquals("Hello Wor", DataType.apply2(Token.Type.SUB, s, 2));
        assertEquals("Hello Wo", DataType.apply2(Token.Type.SUB, s, 3));
        assertEquals("Hello W", DataType.apply2(Token.Type.SUB, s, 4));
        assertEquals("", DataType.apply2(Token.Type.SUB, s, 11));
    }

    @Test
    public void testIntPlusInt() {
        DataType.init();
        assertEquals((Integer)47, DataType.apply2(Token.Type.ADD, 12, 35));
    }

    @Test
    public void testStringMinusString() {
        DataType.init();
        assertEquals("o World", DataType.apply2(Token.Type.SUB, "Hello World", "Hell"));
    }

    @Test
    public void testStringMinusChar() {
        DataType.init();
        assertEquals("Hell Wrld", DataType.apply2(Token.Type.SUB, "Hello World", 'o'));
    }

    @Test
    public void testStringTimesInt() {
        DataType.init();
        var s = "Abc";

        assertEquals("Abc", DataType.apply2(Token.Type.MULT, s, 1));
        assertEquals("", DataType.apply2(Token.Type.MULT, s, 0));
        assertEquals("cbA", DataType.apply2(Token.Type.MULT, s, -1));
        assertEquals("AbcAbcAbc", DataType.apply2(Token.Type.MULT, s, 3));
        assertEquals("cbAcbAcbA", DataType.apply2(Token.Type.MULT, s, -3));
    }

    @Test
    public void testArrayPlusInt() {
        DataType.init();

        var arr = new Object[] {10, 0.5, "Abc"};
        var exp = new Object[] {13, 3.5, "Abc3"};

        assertArrayEquals(exp, DataType.apply2(Token.Type.ADD, arr, 3));
    }
}