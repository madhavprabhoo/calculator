package calculator;

// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // create a lexer that feeds off of input CharStream
        CalculatorLexer lexer     = new CalculatorLexer(CharStreams.fromString(args[0]));
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens  = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer
        CalculatorParser parser   = new CalculatorParser(tokens);

        ParseTree tree = parser.eval(); // begin parsing at eval rule

        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
    }
}
