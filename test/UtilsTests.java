package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utils.Utils;

public class UtilsTests {
    
    @Test
    public void testBalancedBrackets() {
        assertEquals(-1, Utils.findBracketsMismatch("(hello) (world)"));
        assertEquals(0, Utils.findBracketsMismatch("static void main(String[] args) {"));
        assertEquals(-1, Utils.findBracketsMismatch("{{{((((()))))}}}"));
        assertEquals(0, Utils.findBracketsMismatch("{{{(([((())))}}}"));
        assertEquals(0, Utils.findBracketsMismatch("}"));
        assertEquals(0, Utils.findBracketsMismatch("[}"));
    }
}
