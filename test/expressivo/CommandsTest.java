/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandsTest {


    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // ensure assertions are enabled using VM argument: -ea
    }

    @Test
    public void testDifferentiateConstant() {
        assertEquals("Differentiation of constant failed",
            "0.0", Commands.differentiate("5", "x"));
    }

    @Test
    public void testDifferentiateVariableSame() {
        assertEquals("Differentiation of same variable failed",
            "1.0", Commands.differentiate("x", "x"));
    }

    @Test
    public void testDifferentiateVariableDifferent() {
        assertEquals("Differentiation of different variable failed",
            "0.0", Commands.differentiate("y", "x"));
    }

    @Test
    public void testDifferentiateAddition() {
        assertEquals("Differentiation of addition failed",
            "(0.0 + 1.0)", Commands.differentiate("5 + x", "x"));
    }

  
    @Test
    public void testDifferentiateInvalidExpression() {
        try {
            Commands.differentiate("5x", "x");
            fail("Invalid expression should throw exception");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testDifferentiateInvalidVariable() {
        try {
            Commands.differentiate("x + 1", "5");
            fail("Invalid variable should throw exception");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testSimplifyConstant() {
        assertEquals("Simplification of constant failed",
            "5.0", Commands.simplify("5", Map.of("x", 3.0)));
    }

    @Test
    public void testSimplifyVariableWithMapping() {
        assertEquals("Simplification of variable with mapping failed",
            "3.0", Commands.simplify("x", Map.of("x", 3.0)));
    }

    @Test
    public void testSimplifyVariableWithoutMapping() {
        assertEquals("Simplification of variable without mapping failed",
            "y", Commands.simplify("y", Map.of("x", 3.0)));
    }

    @Test
    public void testSimplifyAddition() {
        assertEquals("Simplification of addition failed",
            "8.0", Commands.simplify("x + 5", Map.of("x", 3.0)));
    }

    @Test
    public void testSimplifyMultiplication() {
        assertEquals("Simplification of multiplication failed",
            "15.0", Commands.simplify("x * 5", Map.of("x", 3.0)));
    }

    @Test
    public void testSimplifyNestedExpressionWithMapping() {
        assertEquals("Simplification of nested expression failed",
            "24.0", Commands.simplify("(x + 3) * 4", Map.of("x", 3.0)));
    }

   
    @Test
    public void testSimplifyExpressionWithExtraVariables() {
        assertEquals("Simplification with extra variables failed",
            "(5.0 * y)", Commands.simplify("x * y", Map.of("x", 5.0, "z", 2.0)));
    }

    @Test
    public void testSimplifyInvalidExpression() {
        try {
            Commands.simplify("5x", Map.of("x", 2.0));
            fail("Invalid expression should throw exception");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testSimplifyNoEnvironment() {
        assertEquals("Simplification without environment failed",
            "x", Commands.simplify("x", Map.of()));
    }

    @Test
    public void testSimplifyEmptyExpression() {
        try {
            Commands.simplify("", Map.of("x", 3.0));
            fail("Empty expression should throw exception");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testSimplifyZeroVariableValue() {
        assertEquals("Simplification with zero variable value failed",
            "0.0", Commands.simplify("x * 5", Map.of("x", 0.0)));
    }

    

    @Test
    public void testSimplifyComplexEnvironment() {
        assertEquals("Simplification with complex environment failed",
            "20.0", Commands.simplify("x * y", Map.of("x", 4.0, "y", 5.0)));
    }

    
    @Test
    public void testSimplifyNoVariables() {
        assertEquals("Simplification with no variables failed",
            "10.0", Commands.simplify("5 + 5", Map.of()));
    }

    @Test
    public void testDifferentiateExpressionWithConstants() {
        assertEquals("Differentiation with constants failed",
            "(0.0 + 0.0)", Commands.differentiate("5 + 3", "x"));
    }

    @Test
    public void testSimplifyWithRedundantMapping() {
        assertEquals("Simplification with redundant mapping failed",
            "10.0", Commands.simplify("x * 2", Map.of("x", 5.0, "z", 3.0)));
    }
}
