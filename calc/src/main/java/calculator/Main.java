package calculator;

// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        
        String expression = null;

        if (args.length == 3 && args[0].equals("-l")) {
            switch (args[1]) {
                case "error": Configurator.setLevel("calculator", Level.ERROR);
                              break;
                case "info" : Configurator.setLevel("calculator", Level.INFO);
                              break;
                case "debug": Configurator.setLevel("calculator", Level.DEBUG);
                              break;
                default:      LOG.error("Unsupported log level, please enter either error, info or debug");
                              LOG.error("Usage: java calculator.Main [-l <error|info|debug>] expression");
                              return;
            }
            expression = args[2];
        } else if (args.length == 1) {
            expression = args[0];
        } else {
            LOG.error("Usage: java calculator.Main [-l <error|info|debug>] expression");
            return;
        }
            
        LOG.debug("Input : {}", expression);

        Main main = new Main();

        try {
            // call calculate method with first parameter passed in
            System.out.println(main.calculate(expression));
        }
        catch(NullPointerException npe) {
            LOG.error("Error handling \"{}\", likely an invalid expression, please follow advice above.", args[0]);
        }
        catch(Exception e) {
            LOG.error("Got an unexpected error, please forward this error to Madhav Prabhoo to improve this program.");
        }
    }

    public Integer calculate(String expression) {
        
        if (expression.isEmpty()) { // nothing to parse, log an error and return 0
            LOG.error("Please provide input");
            return 0;
        }

        // create a lexer that feeds off of input CharStream
        CalculatorLexer lexer     = new CalculatorLexer(CharStreams.fromString(expression));
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens  = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer
        CalculatorParser parser   = new CalculatorParser(tokens);

        ParseTree tree = parser.eval(); // begin parsing at eval rule

        LOG.debug("Parse tree for the input : \n{}", tree.toStringTree(parser));

        EvalVisitor eval   = new EvalVisitor();
        Integer     result = eval.visit(tree);

        LOG.debug("Result : {}", result);

        return result;
    }
}
