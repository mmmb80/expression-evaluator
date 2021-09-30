import java.util.Queue;

/**
 * Given an expression, the program prints the parse tree and the value of the expression.
 * The ! postfix operator stands for exponentiation (with base e), other operations are self-explanatory.
 */
public class Main {


    public static void main(String[] args) {
        State.init();
        ParserState.init();
        try
        {
            String expression = "1.0+2.*4";

            Queue<Token> tokens = new Lexer(expression).lex();
            System.out.println("Number of tokens: \n" + tokens.size());
            Symbol res = new Parser().parse(tokens);
            System.out.println("Parse tree:");
            System.out.println(res);
            System.out.println("Value is:");
            System.out.println(res.evaluate());

        }
        catch (UnsupportedOperationException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
