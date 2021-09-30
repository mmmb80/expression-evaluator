import java.util.Queue;
import java.util.Stack;

public class Parser {

    Stack<Symbol> symbols;
    Stack<ParserState> states;


    Parser()
    {
        symbols = new Stack<>();
        states = new Stack<>();
    }


    /**
     * Parsing
     * @param input The output of the lexer
     * @return The parse tree
     */
    Symbol parse (Queue<Token> input)
    {
        input.add(new Token("$"));
        Token lookahead = input.remove();
        states.push(ParserState.parserMachine.get(0));
        while(true)
        {
            boolean stop=false;
            while(states.size()>0 && states.peek().action.get(lookahead.name)==null)
            {

                boolean success=false;
                try
                {


                    if (!states.peek().accepting && !lookahead.name.equals("$")) throw new UnsupportedOperationException("Parsing error: symbol " + lookahead.name+ " is invalid at this position");
                    else if (!states.peek().accepting) throw new UnsupportedOperationException("Parsing error: characters are missing probably");
                    for (AugmentedRule rule: states.peek().ruleSet)
                    {
                        if (rule.dot==rule.rhs.size())
                        {
                           if (! rule.apply(symbols, states)) stop=true;

                            success=true;
                            break;
                        }
                    }
                    if (!success) throw new UnsupportedOperationException("Parser error: you shouldn't see this message");

                }
                catch (Exception e)
                {
                    if (e.getMessage().length()>0) throw new UnsupportedOperationException(e.getMessage());
                    throw new UnsupportedOperationException("Parsing error: unexpected error");
                }
                if (stop) break;
            }


            if (lookahead.toString().equals("$")) break;
            else if (stop) throw new UnsupportedOperationException("Parser error: unexpected extra characters at the end ot input:" + lookahead + input);
            symbols.push(lookahead);

            if (states.empty()) throw new UnsupportedOperationException("Parser error: unexpected error");
            ParserState p = states.peek().action.get(lookahead.name);

            if (p==null) throw new UnsupportedOperationException("Parser error: unexpected extra characters at the end ot input:" + lookahead + input);;
            states.push(p);
            lookahead = input.remove();
        }

        if (symbols.size()!=1) throw new UnsupportedOperationException("Parser error: characters may be missing");

        return symbols.peek();


    }



}
