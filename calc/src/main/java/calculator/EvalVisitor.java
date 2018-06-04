package calculator;

/***
 * Class which visits interesting elements of the parse tree
 * representing the input expression
 * It uses a HashMap to store variables and its values
 * 
***/
import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends CalculatorBaseVisitor<Integer> {
    /** "variables" for our calculator; variable/value pairs go here */
    Map<String, Integer> variables = new HashMap<String, Integer>();

    /** Used to print the output */
	@Override
    public Integer visitEval(CalculatorParser.EvalContext ctx) {
        Integer value = visit(ctx.mathExp()); // evaluate the mathExp child
        System.out.println(value);            // print the result
        return 0;                             // return dummy value
    }

    /** let( ID, NUM|ID|mathExp, mathExp ) */
    @Override
    public Integer visitLet(CalculatorParser.LetContext ctx) {
        String id     = ctx.ID().getText();      // id is the first operand of let
        int var_value = visit(ctx.mathExp(0));   // compute value of expression in second operand
        variables.put(id, var_value);            // store it in our variable map
        int ret_value = visit(ctx.mathExp(1));   // compute value of expression in the third operand
        return ret_value;
    }

    /** add( mathExp, mathExp ) */
    @Override
    public Integer visitAdd(CalculatorParser.AddContext ctx) {
        int first  = visit(ctx.mathExp(0));  // get value of first subexpression
        int second = visit(ctx.mathExp(1));  // get value of second subexpression
        return first + second;
    }

    /** sub( mathExp, mathExp ) */
    @Override
    public Integer visitSub(CalculatorParser.SubContext ctx) {
        int first  = visit(ctx.mathExp(0));  // get value of first subexpression
        int second = visit(ctx.mathExp(1));  // get value of second subexpression
        return first - second;
    }

    /** mult( mathExp, mathExp ) */
    @Override
    public Integer visitMult(CalculatorParser.MultContext ctx) {
        int first  = visit(ctx.mathExp(0));  // get value of first subexpression
        int second = visit(ctx.mathExp(1));  // get value of second subexpression
        return first * second;
    }

    /** div( mathExp, mathExp ) */
    @Override
    public Integer visitDiv(CalculatorParser.DivContext ctx) {
        int first  = visit(ctx.mathExp(0));  // get value of first subexpression
        int second = visit(ctx.mathExp(1));  // get value of second subexpression
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
        System.out.println("Variable " + id + " not defined, assuming 0 as its value");
        return 0;
    }
}
