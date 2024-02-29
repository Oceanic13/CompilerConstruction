package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.Utils;

public class UtilsTests {
    
    @Test
    public void testBalancedBrackets() {
        assertTrue(Utils.hasBalancedBrackets("(hello) (world)"));
        assertFalse(Utils.hasBalancedBrackets("static void main(String[] args) {"));
        assertTrue(Utils.hasBalancedBrackets("{{{((((()))))}}}"));
        assertFalse(Utils.hasBalancedBrackets("{{{(([((())))}}}"));
        assertFalse(Utils.hasBalancedBrackets("}"));
        assertFalse(Utils.hasBalancedBrackets("[}"));
    }
}
