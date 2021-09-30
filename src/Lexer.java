import java.util.ArrayDeque;
import java.util.Queue;

public class Lexer {
    String expr;
    int pos=0;
    char lookahead;
    String buffer = new String();
    Lexer (String str)
    {
        expr = str;
        lookahead = str.charAt(0);
    }

    /**
     * Getting to the next input characters
     * @return false if we reached the end of the string, true otherwise
     */
    boolean shift ()
    {
        pos++;
        buffer = buffer + lookahead;
        if (pos>=expr.length()) return false;

        lookahead = expr.charAt(pos);
        return true;
    }

    /**
     * Lexing the input
     * @return A queue of tokens produced by the lexer
     */
    Queue <Token> lex ()
    {
        Queue<Token> res = new ArrayDeque<>();

        State state = State.machine.get(0);
        while(pos<expr.length())
        {
            if (lookahead == ' ' && !shift()) break;
            else
            {

                Integer nextState = state.transitions.get(lookahead);
                if (nextState==null)
                {
                    res.add(state.accept(buffer));
                    state = State.machine.get(state.defTransit);
                    buffer = new String();
                }
                else
                {
                    state = State.machine.get(nextState);
                    shift();
                }
            }
        }
        res.add(state.accept(buffer));
        return res;
    }



}
