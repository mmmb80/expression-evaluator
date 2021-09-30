import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * States of the FSM for the lexer
 * transitions: define transitions on characters
 * defTransit: defines transitions for other characters after accepting a token (only for accepting states)
 * emission: defines the token created for an accepting state
 *
 */
public class State {



    static List<State> machine = new ArrayList<State>();
    private static int count =0;

    /**
     * initializes the machine
     */
    static void init ()
    {
        if (!machine.isEmpty())  return;
        /**
        *Two "default" states: State 0 expecting infix operators and numbers, State 1 expects infix or postfix operators
         * This is the way to distinguish signs from subtraction
         */
        machine.add(new State(0,new HashMap<>(), false,"" ,-1));
        machine.add(new State(0,new HashMap<>(), false,"" ,-1));
        count=1;
        addStates("+",1,0);
        addStates("-",1,0);
        addStates("*",1,0);
        addStates("!",1,1);
        addStates("(", 0, 0);
        addStates(")",1,1);
        addStates("cos", 0, 0);
        addFloatingPoint(0,1);
        
    }

    /**
     * Add states to define an accepting String (all accepted strings should have different first characters)
     * @param s input string
     * @param startState defines the state of the machine when state is expected
     * @param acceptState defines the defTransit of the accepting states
     */
    static void addStates (String s, int startState, int acceptState)
    {
        machine.get(startState).transitions.put(s.charAt(0),count+1);
        int len = s.length();
        for (int i=0;i<len;i++)
        {
            int idOf=count++;
            if (i+1==len)
            {
                machine.add(new State(idOf, new HashMap<>(), true, s, acceptState));
                
            }
            else
            {
                HashMap<Character, Integer> hmap = new HashMap<>();
                hmap.put(s.charAt(i+1),count+1);
                machine.add(new State(idOf, hmap, false, "", -1));
            }
        }
    }

    /**
     * Add states to the FSM for accepting floating point numbers
     * @param startState defines the state of the machine when floating point number is expected
     * @param acceptState defines the defTransit of the accepting states
     */
    static void addFloatingPoint(int startState, int acceptState)
    {
        machine.get(startState).transitions.put('+',count+1);
        machine.get(startState).transitions.put('-',count+1);
        machine.add(new State(++count, new HashMap<>(), false, "", -1));
        for (char i='0';i<='9';i++)
        {
            machine.get(startState).transitions.put(i,count+1);
            machine.get(count).transitions.put(i,count+1);

        }
        machine.add(new State(++count, new HashMap<>(), true, "float", acceptState));
        for (char i='0';i<='9';i++)
        {

            machine.get(count).transitions.put(i,count);
        }
        machine.get(count).transitions.put('.', count+1);
        machine.add(new State(++count , new HashMap<>(), true, "float", acceptState));
        for (char i='0';i<='9';i++)
        {

            machine.get(count).transitions.put(i,count);
        }
        machine.get(count-1).transitions.put('E', count+1);
        machine.get(count).transitions.put('E', count+1);
        machine.add(new State(++count , new HashMap<>(), false, "", -1));
        machine.get(count).transitions.put('+', count+1);
        machine.get(count).transitions.put('-', count+1);
        for (char i='0';i<'9';i++)
        {

            machine.get(count).transitions.put(i,count+2);

        }
        machine.add(new State(++count , new HashMap<>(), false, "", -1));
        for (char i='0';i<='9';i++)
        {

            machine.get(count).transitions.put(i,count+1);

        }
        machine.add(new State(++count , new HashMap<>(), true, "float", acceptState));

        for (char i='0';i<='9';i++)
        {

            machine.get(count).transitions.put(i,count);

        }





    }
    int id;
    HashMap<Character, Integer> transitions;
    boolean accepting;
    String emission;
    Integer defTransit;
    
    State (int i, HashMap<Character, Integer> map, boolean a, String em, Integer dt)
    {
        id=i; transitions=map; accepting=a; emission=em; defTransit=dt;
    }

    /**
     * Accepting a token
     * @param buffer the characters not yet processed by the lexer
     * @return The token created
     */
    Token accept(String buffer)
    {
        if (!accepting) throw new UnsupportedOperationException("Lexer error " + buffer +" ");
        if (emission.equals("float")) return new Id(buffer);

        return new Token (emission);
    }


}
