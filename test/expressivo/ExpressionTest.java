/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    private final Expression zero = new Number(0);
    private final Expression one = new Number(1);
    private final Expression two = new Number(2);
    private final Expression x = new Variable("x");
    private final Expression y = new Variable("y");

    private final Expression sumExp = new Operation('+', one, x);
    private final Expression prodExp = new Operation('*', x, one);
    private final Expression complexExp = new Operation('*', sumExp, prodExp);

    @Test
    public void testAssertionsEnabled() {
        boolean assertionEnabled = false;
        try {
            assert false;
        } catch (AssertionError e) {
            assertionEnabled = true;
        }
        assertTrue("Assertions should be enabled", assertionEnabled);
    }

    @Test
    public void testNumberToString() {
        assertEquals("Number should convert to string correctly", "1.0", one.toString());
    }

    @Test
    public void testVariableToString() {
        assertEquals("Variable should convert to string correctly", "x", x.toString());
    }

    @Test
    public void testSimpleSumToString() {
        assertEquals("Sum expression should convert to string correctly", "(1.0 + x)", sumExp.toString());
    }

    @Test
    public void testSimpleProductToString() {
        assertEquals("Product expression should convert to string correctly", "(x * 1.0)", prodExp.toString());
    }

    @Test
    public void testComplexExpressionToString() {
        assertEquals("Complex expression should convert to string correctly", "((1.0 + x) * (x * 1.0))", complexExp.toString());
    }

    @Test
    public void testEqualityOfNumbers() {
        assertEquals("Numbers should be equal if they represent the same value", new Number(1.0), one);
    }

    @Test
    public void testEqualityOfVariables() {
        assertEquals("Variables should be equal if they have the same name", new Variable("x"), x);
    }

    @Test
    public void testEqualityOfOperations() {
        Expression exp = new Operation('+', new Number(1.0), new Variable("x"));
        assertEquals("Operation expressions should be equal if structure matches", sumExp, exp);
    }

    @Test
    public void testNumberInequality() {
        assertNotEquals("Numbers with different values should not be equal", one, new Number(2.0));
    }

    @Test
    public void testVariableInequality() {
        assertNotEquals("Variables with different names should not be equal", x, new Variable("y"));
    }

    @Test
    public void testOperationInequalityByType() {
        Expression exp = new Operation('*', new Number(1.0), new Variable("x"));
        assertNotEquals("Operations with different types should not be equal", sumExp, exp);
    }

    @Test
    public void testOperationInequalityByStructure() {
        Expression exp = new Operation('+', x, one);
        assertNotEquals("Operations with different structures should not be equal", sumExp, exp);
    }

    @Test
    public void testParseNumber() {
        assertEquals("Parsing a number should yield the correct expression", new Number(1.0), Expression.parse("1"));
    }

    @Test
    public void testParseVariable() {
        assertEquals("Parsing a variable should yield the correct expression", new Variable("x"), Expression.parse("x"));
    }

    @Test
    public void testParseSum() {
        assertEquals("Parsing a sum should yield the correct expression", sumExp, Expression.parse("1 + x"));
    }

    @Test
    public void testParseProduct() {
        assertEquals("Parsing a product should yield the correct expression", prodExp, Expression.parse("x * 1"));
    }

    @Test
    public void testParseComplexExpression() {
        assertEquals("Parsing a complex expression should yield the correct structure", complexExp, Expression.parse("(1 + x) * (x * 1)"));
    }

    @Test
    public void testDifferentiateNumber() {
        assertEquals("Differentiating a number should yield zero", zero, one.differentiate("x"));
    }

    @Test
    public void testDifferentiateVariable() {
        assertEquals("Differentiating a variable with respect to itself should yield one", one, x.differentiate("x"));
    }

    @Test
    public void testDifferentiateSum() {
        Expression result = new Operation('+', zero, one);
        assertEquals("Differentiating a sum should apply the sum rule", result, sumExp.differentiate("x"));
    }

    @Test
    public void testSimplifyNumber() {
        assertEquals("Simplifying a number should return itself", one, one.simplify(Map.of("x", 2.0)));
    }

    @Test
    public void testSimplifyVariable() {
        assertEquals("Simplifying a variable with a value should substitute the value", two, x.simplify(Map.of("x", 2.0)));
    }

   

    @Test
    public void testSimplifyProductWithZero() {
        Expression exp = new Operation('*', zero, x);
        assertEquals("Simplifying a product with zero should return zero", zero, exp.simplify(Map.of("x", 3.0)));
    }
}
