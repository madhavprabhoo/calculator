package calculator;

import org.junit.rules.TestName;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test succes cases, failure cases, invalid cases, warnings
 */
public class CalculatorTest {

    @Rule
    public TestName testName = new TestName();

    private calculator.Main main;
 
    /** Sets up the test fixture. Create here ojects needed before every test case method. */
    @Before
    public void setUp() {
        main = new calculator.Main();
        System.out.println("Running test : " + testName.getMethodName());
    }
 
    /** Tears down the test fixture. Called after every test case method. */
    @After
    public void tearDown() {
        main = null;
    }
    
    /** Test for add expression */
    @Test
    public void add() throws Exception {
        int expected = 3;
        assertEquals("add(1,2) should return 3", expected, (int) main.calculate("add(1,2)"));
    }
 
    /** Test for sub expression */
    @Test
    public void sub() throws Exception {
        int expected = 3;
        assertEquals("sub(5,2) should return 3", expected, (int) main.calculate("sub(5,2)"));
    }
 
    /** Test for mult expression */
    @Test
    public void mult() throws Exception {
        int expected = 10;
        assertEquals("mult(5,2) should return 10", expected, (int) main.calculate("mult(5,2)"));
    }
 
    /** Test for div expression */
    @Test
    public void div() throws Exception {
        int expected = 10;
        assertEquals("div(20,2) should return 10", expected, (int) main.calculate("div(20,2)"));
    }
 
    /** Test for oneSubExpression expression */
    @Test
    public void oneSubExpression() throws Exception {
        int expected = 3;
        assertEquals("add(1,sub(7,5)) should return 3", expected, (int) main.calculate("add(1,sub(7,5))"));
    }
 
    /** Test for twoSubExpressions expression */
    @Test
    public void twoSubExpressions() throws Exception {
        int expected = 10;
        assertEquals("add(mult(4,2),sub(7,5)) should return 10", expected, (int) main.calculate("add(mult(4,2),sub(7,5))"));
    }
 
    /** Test for let expression */
    @Test
    public void let() throws Exception {
        int expected = 10;
        assertEquals("let(a,5,add(a,a)) should return 10", expected, (int) main.calculate("let(a,5,add(a,a))"));
    }
 
    /** Test for letTwoExpressions expression */
    @Test
    public void letTwoExpressions() throws Exception {
        int expected = 10;
        assertEquals("let(a,mult(4,2),add(a,2)) should return 10", expected, (int) main.calculate("let(a,mult(4,2),add(a,2))"));
    }
 
    /** Test for largeLet expression */
    @Test
    public void largeLet() throws Exception {
        int expected = 40;
        assertEquals("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))) should return 40", expected, (int) main.calculate("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))"));
    }
 
    /** Test for singleLiteral expression */
    @Test
    public void singleLiteral() throws Exception {
        int expected = 10;
        assertEquals("10 should return 10", expected, (int) main.calculate("10"));
    }
 
    /** Test for empty expression */
    @Test
    public void empty() throws Exception {
        int expected = 0;
        assertEquals("\"\" should return 0", expected, (int) main.calculate(""));
    }
 
    /** Test for singleVariable expression */
    @Test
    public void singleVariable() throws Exception {
        int expected = 0;
        assertEquals("a should return 0", expected, (int) main.calculate("a"));
    }
 
    /** Test for invalidInput1 expression */
    @Test
    public void invalidInput1() throws Exception {
        int expected = 10;
        assertEquals("add(8,sub(7,5) should return 10", expected, (int) main.calculate("add(8,sub(7,5)"));
    }
 
    /** Test for invalidInput2 expression */
    @Test
    public void invalidInput2() throws Exception {
        int expected = 8;
        assertEquals("(mult(4,2),sub(7,5)) should return 8", expected, (int) main.calculate("(mult(4,2),sub(7,5))"));
    }
 
    /** Test for letVariableNotUsed expression */
    @Test
    public void letVariableNotUsed() throws Exception {
        int expected = 2;
        assertEquals("let(a,mult(4,2),sub(7,5)) should return 2", expected, (int) main.calculate("let(a,mult(4,2),sub(7,5))"));
    }
 
    /** Test for undefinedVariable expression */
    @Test
    public void undefinedVariable() throws Exception {
        int expected = 7;
        assertEquals("let(a,mult(4,2),sub(7,c)) should return 7", expected, (int) main.calculate("let(a,mult(4,2),sub(7,c))"));
    }
 
    /** Test for invalid expression */
    @Test(expected=NullPointerException.class)
    public void invalidInput() {
        main.calculate("add(1,sub(7,");
    }
}
