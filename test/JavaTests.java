package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JavaTests {
    
    @Test
    public void testInstanceStuff() {
        Integer one = 1;
        Double pi = Math.PI;
        assertTrue(one instanceof Integer);
        assertTrue(one instanceof Number);
        assertTrue(pi instanceof Double);
        assertTrue(pi instanceof Number);
    }
}
