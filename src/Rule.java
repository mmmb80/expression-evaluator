import java.util.*;

public class Rule {

    static List<Rule> rules = new ArrayList<>();

    static void init()
    {
        if (!rules.isEmpty()) return;
        /**
         * Defining the set of rules
         */
        rules.add(new Rule("A", Arrays.asList("B")));
        rules.add(new Rule("A", Arrays.asList("A","+","B")));
        rules.add(new Rule("B", Arrays.asList("C")));
        rules.add(new Rule("B", Arrays.asList("B","-","C")));
        rules.add(new Rule("C", Arrays.asList("D")));
        rules.add(new Rule("C", Arrays.asList("D","*","C")));
        rules.add(new Rule("D", Arrays.asList("E")));
        rules.add(new Rule("D", Arrays.asList("cos","D")));
        rules.add(new Rule("E", Arrays.asList("F")));
        rules.add(new Rule("E", Arrays.asList("E","!")));
        rules.add(new Rule("F", Arrays.asList("id")));
        rules.add(new Rule("F", Arrays.asList("(","A",")")));

    }

    String lhs;
    List<String> rhs;

    @Override
    public String toString() {

        String rhsString="";

        for (String c: rhs)
        {
            rhsString+=c;
        }

        return
                 lhs + "->" +
                 rhsString ;
    }

    /**
     * Equality checking for rules (even for augmented rules where dots are on different position)
     * @param o
     * @return
     */
    public boolean sameRule(Rule o) {
        if (this == o) return true;
        if (o == null) return false;
        Rule rule = o;
        return Objects.equals(lhs, rule.lhs) &&
                Objects.equals(rhs, rule.rhs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lhs, rhs);
    }

    boolean apply (Stack<Symbol> stack, Stack<ParserState> states)
    {
        Nonterminal newItem = new Nonterminal(lhs);
        for (int x = rhs.size()-1; x>=0; x--)
        {
           Symbol top = stack.pop();
           states.pop();
           if (!top.name.equals(rhs.get(x))) throw new UnsupportedOperationException("wrong rule application");
           newItem.children.add(top);
        }
        Collections.reverse(newItem.children);
        stack.push(newItem);
        ParserState g = states.peek().goTo.get(this.lhs);
        if (g==null) return false;
        states.push(g);
        return true;
    }

    Rule(String lhs, List<String> rhs)
    {
     this.lhs=lhs; this.rhs=rhs;
    }



}
