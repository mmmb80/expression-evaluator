import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AugmentedRule extends Rule {


    static List<AugmentedRule> augmentedRules = new ArrayList<>();

    static void init()
    {
        if (!augmentedRules.isEmpty()) return ;

        Rule.init();
        augmentedRules = augmentedGrammar(Rule.rules, "A");
    }

    int dot;

    AugmentedRule(String lhs, List<String> rhs) {
        super(lhs, rhs);
        dot=0;
    }

    @Override
    public String toString ()
    {
        String res = lhs + "->" ;
        for (int i=0;i<=rhs.size();i++)
        {
            if (dot==i) res+=".";
            if (i<rhs.size()) res+=rhs.get(i);
        }
        return res;
    }

    AugmentedRule(Rule r, int dot)
    {
        super(r.lhs, r.rhs);
        this.dot=dot;

    }

    AugmentedRule(String lhs ,List<String> rhs, int dot)
    {
        super(lhs, rhs);
        this.dot=dot;
    }

    /**
     * Creates augmented grammar from the grammar defined
     * @param grammar the original grammar
     * @param startSymbol the starting point of the grammar
     * @return an augmented grammar with rule S'-> .startSymbol as the last element
     */
    static List <AugmentedRule> augmentedGrammar (List<Rule> grammar, String startSymbol)
    {
        List<AugmentedRule> res = new ArrayList<>();
        grammar.add(new Rule("S'", Arrays.asList(new String[]{startSymbol})));
        for (Rule rule : grammar)
        {
            for (int i=rule.rhs.size();i>=0; i--) res.add(new AugmentedRule(rule,i));
        }
        return res;

        //ASSERT: last element is S'-> . startSymbol
    }


}
