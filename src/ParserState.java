import java.util.*;

public class ParserState {

    static List<ParserState> parserMachine = new ArrayList<>();

    static void init()
    {
        if (!parserMachine.isEmpty()) return;

        AugmentedRule.init();

        AugmentedRule start = AugmentedRule.augmentedRules.get(AugmentedRule.augmentedRules.size()-1);
        Set<AugmentedRule> set = new HashSet<>();
        set.add(start);
        closure(set);

    }


    Set<AugmentedRule> ruleSet;
    Map<String, ParserState> action;
    Map<String, ParserState> goTo;
    boolean accepting = false;
    int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParserState that = (ParserState) o;
        return Objects.equals(ruleSet, that.ruleSet);
    }


    @Override
    public int hashCode() {
        return Objects.hash(ruleSet);
    }

    public ParserState(Set<AugmentedRule> ruleSet, Map<String, ParserState> action, Map<String, ParserState> goTo) {
        this.ruleSet = ruleSet;
        this.action = action;
        this.goTo = goTo;
    }

    /**
     * Finds closure of a set of (augmented) rules
     * @param rules
     * @return a state of the parser machine
     */
    static ParserState closure(Set<AugmentedRule> rules)
    {

        boolean iterate=true;
        boolean accepting=false;
        while (iterate)
        {
            int setSize = rules.size();
            iterate=false;
            Set<AugmentedRule> newRules = new HashSet<>();
            for (AugmentedRule rule : rules)
            {
                if (rule.dot==rule.rhs.size()) accepting=true;
                else
                {
                    String symbol = rule.rhs.get(rule.dot);

                    for (AugmentedRule allRules : AugmentedRule.augmentedRules)
                    {
                        if (allRules.dot==0 && allRules.lhs.equals(symbol)) newRules.add(allRules);
                    }
                }
            }
            rules.addAll(newRules);

            if (setSize<rules.size()) iterate=true;

        }
        ParserState res = new ParserState(rules, new HashMap<>(), new HashMap<>());
        res.accepting=accepting;
        for (ParserState state : parserMachine) if (res.equals(state)) return state;

        res.id=parserMachine.size();
        parserMachine.add(res);

        for (String terminal : Token.tokenNames)
        {
            Set<AugmentedRule> actionRules = new HashSet<>();
            for (AugmentedRule rule: rules)
            {
                if (rule.dot<rule.rhs.size() && rule.rhs.get(rule.dot).equals(terminal))
                {
                    for (AugmentedRule rule2: AugmentedRule.augmentedRules)
                    {
                        if (rule.sameRule(rule2) && rule2.dot==rule.dot+1) actionRules.add(rule2);

                    }
                }
            }

            if (!actionRules.isEmpty()) res.action.put(terminal, closure(actionRules));

        }

        for (String nonterminal : Nonterminal.nonterminalNames)
        {
            Set<AugmentedRule> goToRules = new HashSet<>();
            for (AugmentedRule rule: rules)
            {
                if (rule.dot<rule.rhs.size() && rule.rhs.get(rule.dot).equals(nonterminal))
                {
                    for (AugmentedRule rule2: AugmentedRule.augmentedRules)
                    {
                        if (rule.sameRule(rule2) && rule2.dot==rule.dot+1) goToRules.add(rule2);

                    }
                }
            }

            if (!goToRules.isEmpty()) res.goTo.put(nonterminal, closure(goToRules));

        }



        return res;

    }


    static Map<String, Integer> simplifyMap (Map<String, ParserState> arg)
    {

        Map<String, Integer> res= new HashMap<>();
        arg.keySet().stream().forEach(x-> res.put(x,arg.get(x).id));
        return res;
    }

    @Override
    public String toString() {
        return "State " + id + "\n" +
                "ruleSet=" + ruleSet +
                "\naction:\n" + simplifyMap(action) +
                "\ngoTo:\n" + simplifyMap(goTo) + '\n'+

                 ((accepting)?"accepting":"") + "\n \n";
    }

    public static void main(String[] args) {
        /**
         * Prints the states of the LR machine.
         */
        init();
        System.out.println(parserMachine.size());
       parserMachine.forEach(System.out::println);
    }
}
