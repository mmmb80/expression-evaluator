import java.util.Queue;

/**
 * Tests some expressions.
 */
public class Tester {

    static boolean invalidCharacterTest ()
    {
        State.init();
        boolean error=false;
        Lexer l = new Lexer("2.+3,5");
        try
        {
            l.lex();
        }
        catch (UnsupportedOperationException e)
        {
            error=true;
        }

        return error;

    }

    static boolean validLexerTest ()
    {
        State.init();
        boolean error=false;
        Lexer l = new Lexer("2.+3.5*cos(3.+5!!!)-1.4E-8");
        try
        {
           Queue<Token> tokens = l.lex();
           if (tokens.size()!=15) error=true;
        }
        catch (UnsupportedOperationException e)
        {
            error=true;
        }

        return !error;
    }

    static boolean wrongParenthesesTest()
    {
        State.init();
        boolean error=false;
        Lexer l = new Lexer("2.+(3.5-5)+5-(4*5");
        Parser p = new Parser();
        ParserState.init();

        try
        {
            p.parse(l.lex());
        }
        catch (UnsupportedOperationException e)
        {
            error=true;
        }

        return error;
    }

    static boolean correctParenthesesTest()
    {
        State.init();
        boolean error=false;
        Lexer l = new Lexer("2.+(3.5-5)+5-(4*5)");
        Parser p = new Parser();
        ParserState.init();

        try
        {
            p.parse(l.lex());
        }
        catch (UnsupportedOperationException e)
        {
            error=true;
        }

        return !error;
    }
    static boolean wrongPostfixTest()
    {
        State.init();
        boolean error=false;
        Lexer l = new Lexer("2.+(3.5-5)+5-!(4*5)");
        Parser p = new Parser();
        ParserState.init();

        try
        {
            p.parse(l.lex());
        }
        catch (UnsupportedOperationException e)
        {
            error=true;
        }

        return error;
    }

    static boolean goodOperationOrderCheck()
    {
        State.init();
        boolean error=false;
        Lexer l = new Lexer("3*4+5*4");
        Parser p = new Parser();
        ParserState.init();

        try
        {
            float s = p.parse(l.lex()).evaluate();
           if (s!=32) error=true;
        }
        catch (UnsupportedOperationException e)
        {
            error=true;
        }

        return !error;
    }

    static boolean nestedPrefixesTest()
    {
        State.init();
        boolean error=false;
        Lexer l = new Lexer("coscoscos3.3+4");
        Parser p = new Parser();
        ParserState.init();

        try
        {
            String s = p.parse(l.lex()).toString();
            if (!s.equals("S'[A[A[B[C[D[cos][D[cos][D[cos][D[E[F[3.3]]]]]]]]]][+][B[C[D[E[F[4]]]]]]]")) error=true;
        }
        catch (UnsupportedOperationException e)
        {
            error=true;
        }

        return !error;
    }



    public static void main(String[] args) {
        int count=0;
        if (invalidCharacterTest()) System.out.println("Test"+ (++count)+" passed");
        else System.out.println("Test"+ (++count)+" failed");

        if (validLexerTest()) System.out.println("Test"+ (++count)+" passed");
        else System.out.println("Test"+ (++count)+" failed");

        if (wrongParenthesesTest()) System.out.println("Test"+ (++count)+" passed");
        else System.out.println("Test"+ (++count)+" failed");

        if (correctParenthesesTest()) System.out.println("Test"+ (++count)+" passed");
        else System.out.println("Test"+ (++count)+" failed");

        if (wrongPostfixTest()) System.out.println("Test"+ (++count)+" passed");
        else System.out.println("Test"+ (++count)+" failed");


        if (goodOperationOrderCheck()) System.out.println("Test"+ (++count)+" passed");
        else System.out.println("Test"+ (++count)+" failed");

        if (nestedPrefixesTest()) System.out.println("Test"+ (++count)+" passed");
        else System.out.println("Test"+ (++count)+" failed");

    }

}
