package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import scanner.Token;
import structs.DataValue;

public class DataValueTests {

    @Test
    public void testDataTypes() {
        DataValue.init();

        assertEquals(Boolean.class, new DataValue<>(true).type());
        assertEquals(Integer.class, new DataValue<>(3).type());
        assertEquals(Double.class, new DataValue<>(10.5).type());
        assertEquals(Character.class, new DataValue<>('?').type());
        assertEquals(String.class, new DataValue<>("Hello World").type());
    }

    @Test
    public void testBoolCasts() {
        DataValue.init();

        var b = new DataValue<>(true);
        assertEquals(true, b.value());
        assertEquals(1, b.cast(Integer.class).value().intValue());
        assertEquals(1., b.cast(Double.class).value().doubleValue(), 0.);
        assertEquals('1', b.cast(Character.class).value().charValue());
        assertEquals("true", b.cast(String.class).value());
    }

    @Test
    public void testIntCasts() {
        DataValue.init();

        var i = new DataValue<>(65);
        assertEquals(true, i.cast(Boolean.class).value());
        assertEquals(65, i.value().intValue());
        assertEquals(65., i.cast(Double.class).value(), 0.);
        assertEquals('A', i.cast(Character.class).value().charValue());
        assertEquals("65", i.cast(String.class).value());
    }

    @Test
    public void testDecCasts() {
        DataValue.init();

        var d = new DataValue<>(69.6);
        assertEquals(true, d.cast(Boolean.class).value());
        assertEquals(69, d.cast(Integer.class).value().intValue());
        assertEquals(69.6, d.value().doubleValue(), 0.);
        assertEquals('E', d.cast(Character.class).value().charValue());
        assertEquals("69.6", d.cast(String.class).value());
    }

    @Test
    public void testCharCasts() {
        DataValue.init();

        var c = new DataValue<>('v');
        assertEquals(true, c.cast(Boolean.class).value());
        assertEquals(118, c.cast(Integer.class).value().intValue());
        assertEquals(118., c.cast(Double.class).value().doubleValue(), 0.);
        assertEquals('v', c.value().charValue());
        assertEquals("v", c.cast(String.class).value());
    }

    @Test
    public void testStrCasts() {
        DataValue.init();

        var s = new DataValue<>("Hello World");
        assertEquals(true, s.cast(Boolean.class).value());
        assertEquals(11, s.cast(Integer.class).value().intValue());
        assertEquals(11., s.cast(Double.class).value().doubleValue(), 0.);
        //assertEquals('H', s.cast(Character.class).value().charValue());
        assertEquals("Hello World", s.value());
    }

    @Test
    public void testNumMinusNum() {
        DataValue.init();
        var i = new DataValue<>(10);
        var d = new DataValue<>(5.5);
        assertEquals(4.5, i.apply(Token.Type.SUB, d).value());
        assertEquals(-4.5, d.apply(Token.Type.SUB, i).value());
    }

    @Test
    public void testIntPlusInt() {
        DataValue.init();
        var a = new DataValue<>(12);
        var b = new DataValue<>(35);
        assertEquals(47, a.apply(Token.Type.ADD, b).value());
    }

    @Test
    public void testStringMinusString() {
        DataValue.init();
        var s1 = new DataValue<>("Hello World");
        var s2 = new DataValue<>("Hell");
        assertEquals("o World", s1.apply(Token.Type.SUB, s2).value());
    }

    @Test
    public void testStringMinusChar() {
        DataValue.init();
        var s = new DataValue<>("Hello World");
        var c = new DataValue<>('o');
        assertEquals("Hell Wrld", s.apply(Token.Type.SUB, c).value());
    }

    @Test
    public void testStringTimesInt() {
        DataValue.init();
        var s = new DataValue<>("Abc");

        assertEquals("Abc", s.apply(Token.Type.MULT, new DataValue<>(1)).value());
        assertEquals("", s.apply(Token.Type.MULT, new DataValue<>(0)).value());
        assertEquals("cbA", s.apply(Token.Type.MULT, new DataValue<>(-1)).value());
        assertEquals("AbcAbcAbc", s.apply(Token.Type.MULT, new DataValue<>(3)).value());
        assertEquals("cbAcbAcbA", new DataValue<>(-3).apply(Token.Type.MULT, s).value());
    }
}