package calculator;

/***
 * Class which visits interesting elements of the parse tree
 * representing the input expression
 * It uses a HashMap to store variables and its values
 * 
***/
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EvalVisitor extends CalculatorBaseVisitor<Integer> {

    private static final Logger LOG = LogManager.getLogger(EvalVisitor.class);

    /** "variables" for our calculator; variable/value pairs go here */
    Map<String, Integer> variables = new HashMap<String, Integer>();

    /** Used to print the output */
    @Override
    public Integer visitEval(CalculatorParser.EvalContext ctx) {
        Integer value = visit(ctx.mathExp()); // evaluate the mathExp child
        LOG.debug("input evaluated to {}", value);          // print value
        // System.out.println(value);
        return value;                         // return dummy value
    }

    /** let( ID, NUM|ID|mathExp, mathExp ) */
    @Override
    public Integer visitLet(CalculatorParser.LetContext ctx) {
        String id     = ctx.ID().getText();      // id is the first operand of let
        LOG.debug("let({},{},{})", id, ctx.mathExp(0).getText(), ctx.mathExp(1).getText());
        int var_value = visit(ctx.mathExp(0));   // compute value of expression in second operand
        variables.put(id, var_value);            // store it in our variable map
        int ret_value = visit(ctx.mathExp(1));   // compute value of expression in the third operand
        LOG.debug("let({},{},{}), returned {}", id, var_value, ctx.mathExp(1).getText(), ret_value);
        return ret_value;
    }

    /** add( mathExp, mathExp ) */
    @Override
    public Integer visitAdd(CalculatorParser.AddContext ctx) {
        int first  = visit(ctx.mathExp(0));  // get value of first subexpression
        int second = visit(ctx.mathExp(1));  // get value of second subexpression
        LOG.debug("add({}, {}), gave result : {}", first, second, first + second);
        return first + second;
    }

    /** sub( mathExp, mathExp ) */
    @Override
    public Integer visitSub(CalculatorParser.SubContext ctx) {
        int first  = visit(ctx.mathExp(0));  // get value of first subexpression
        int second = visit(ctx.mathExp(1));  // get value of second subexpression
        LOG.debug("sub({}, {}), gave result : {}", first, second, first - second);
        return first - second;
    }

    /** mult( mathExp, mathExp ) */
    @Override
    public Integer visitMult(CalculatorParser.MultContext ctx) {
        int first  = visit(ctx.mathExp(0));  // get value of first subexpression
        int second = visit(ctx.mathExp(1));  // get value of second subexpression
        LOG.debug("mult({}, {}), gave result : {}", first, second, first * second);
        return first * second;
    }

    /** div( mathExp, mathExp ) */
    @Override
    public Integer visitDiv(CalculatorParser.DivContext ctx) {
        int first  = visit(ctx.mathExp(0));  // get value of first subexpression
        int second = visit(ctx.mathExp(1));  // get value of second subexpression
        LOG.debug("div({}, {}), gave result : {}}", first, second, first / second);
        return first / second;
    }

    /** INT */
    @Override
    public Integer visitInteger(CalculatorParser.IntegerContext ctx) {
        return Integer.valueOf(ctx.NUM().getText());
    }

    /** ID */
    @Override
    public Integer visitId(CalculatorParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if ( variables.containsKey(id) ) return variables.get(id);
        // TODO Variable is defined but not used
        LOG.error("Variable {} not defined, assuming 0 as its value", id);
        return 0;
    }
}
