# Simple command line calculator

## Assignment Goal

A Java program that evaluates expressions in a very simple integer expression language. The program takes an input
 on the command line, computes the result, and prints it to the console. For example:

    % java calculator.Main "add(2, 2)"
    4

Few more examples:

    Input                                                      Output
    add(1, 2)                                                    3
    add(1, mult(2, 3))                                           7
    mult(add(2, 2), div(9, 3))                                   12
    let(a, 5, add(a, a))                                         10
    let(a, 5, let(b, mult(a, 10), add(b, a)))                    55
    let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))          40

An expression is one of the of the following:
* Numbers: integers between Integer.MIN_VALUE and Integer.MAX_VALUE
* Variables: strings of characters, where each character is one of a-z, A-Z
* Arithmetic functions: add, sub, mult, div, each taking two arbitrary expressions as arguments. In other words, each argument may be any of the expressions on this list.
* A “let” operator for assigning values to variables:


    let(\<variable name\>, \<value expression\>, \<expression where variable is used\>)

As with arithmetic functions, the value expression and the expression where the variable is used may be an arbitrary expression from this list.

## Usage

    $ java calculator.Main [-l <error|info|debug>] expression
    
    Here -l allows you to specify logging level, default logging level is error.
    If expression contains whitespace please wrap it in quotes

Examples of basic usage:

    $ java calculator.Main "mult(add(2, 2), div(9, 3))"
    12

Examples of using logging levels:

    $ java calculator.Main -l debug "let(a, 10, add(a, 6)))"
    18:22:35.566 [main] DEBUG calculator.Main - Input : let(a, 10, add(a, 6)))
    18:22:35.848 [main] DEBUG calculator.Main - Parse tree for the input : 
    (eval (mathExp let ( a , (mathExp 10) , (mathExp add ( (mathExp a) , (mathExp 6) )) )))
    18:22:35.858 [main] DEBUG calculator.EvalVisitor - let(a,10,add(a,6))
    18:22:35.859 [main] DEBUG calculator.EvalVisitor - add(10, 6), gave result : 16
    18:22:35.861 [main] DEBUG calculator.EvalVisitor - let(a,10,add(a,6)), returned 16
    18:22:35.862 [main] DEBUG calculator.EvalVisitor - input evaluated to 16
    18:22:35.862 [main] DEBUG calculator.Main - Result : 16
    16

    $ java calculator.Main -l info "let(a, 10, add(b, 6)))"
    18:23:42.978 [main] INFO  calculator.EvalVisitor - Variable b not defined, assuming 0 as its value
    6


## Installation

### Cloning and setting up environment
Clone the project as follows:

    $ git clone https://github.com/madhavprabhoo/calculator.git
    
Make sure you have $JAVA_HOME (Java 1.8) and $CLASSPATH set, here is an example of how CLASSPATH can be set:

    $ cd calculator/calc
    $ export CLASSPATH=.:./jars/calc-1.0-SNAPSHOT-jar-with-dependencies.jar:$CLASSPATH

Now you can run the calculator as below:

    $ java calculator.Main "let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))"
    40


## Libraries and Tools used in this assignment

Development was done on CentOS 6, the following open source software was used

    1. Java 1.8 (JDK, JRE)
    2. Maven 3.5.3
    3. Antlr 4.7.1
    4. Log4j 2.11
    5. JUnit 4.12
    6. Git
    
## Notes

Solving of expression is done in a "permissive" attempt. The approach for invalid expressions was to make a best attempt with the given information, and provide an answer the portion of the expression which can be solved, make assumption and log them as at level info to make progress.

## Unit Tests

A variety of unit tests, both positive and negative test cases, have been written using JUnit. These can be found in class [calculator/calc/src/test/java/calculator/CalculatorTest.java](calc/src/test/java/calculator/CalculatorTest.java#L32), to perform the tests run the following:

    $ cd calculator/calc
    $ mvn test
    ...
    ...
    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running calculator.CalculatorTest
    Running test : singleLiteral
    Running test : largeLet
    Running test : invalidInput1
    line 1:14 missing ')' at '<EOF>'
    Running test : invalidInput2
    line 1:0 extraneous input '(' expecting {'let', 'add', 'sub', 'mult', 'div', ID, NUM}
    Running test : singleVariable
    Running test : twoSubExpressions
    Running test : add
    Running test : div
    Running test : let
    Running test : sub
    Running test : mult
    Running test : empty
    18:43:33.430 [main] ERROR calculator.Main - Please provide input
    Running test : invalidInput
    line 1:12 mismatched input '<EOF>' expecting {'let', 'add', 'sub', 'mult', 'div', ID, NUM}
    Running test : undefinedVariable
    Running test : oneSubExpression
    Running test : letVariableNotUsed
    Running test : letTwoExpressions
    Tests run: 17, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.357 sec

    Results :

    Tests run: 17, Failures: 0, Errors: 0, Skipped: 0
