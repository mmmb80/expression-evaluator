
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Objects of this class are roots of subtrees in the parse tree.
 */
public class Nonterminal extends Symbol{

    static List<String> nonterminalNames = Arrays.asList(new String[]{"S'","A","B", "C", "D", "E", "F"});

    List<Symbol> children;

    Nonterminal(String n)
    {
        name = n;
        children = new ArrayList<Symbol>();

    }

    Nonterminal (String n, List<Symbol> li)
    {
        name=n;
        children = li;

    }

    /**
     *
     * @return the subtree of the nonterminal in format name [child1] [child2] ... [childN]
     */
    @Override
    public String toString() {
        String a = name ;



        for (Symbol c : children)
        {
            a+='[';
            a+=c;
            a+=']';
        }

        return a;
    }

    @Override
    float evaluate() {
        if (children.size()==1)  return children.get(0).evaluate();
        switch (name)
        {

            case "A" : return children.get(0).evaluate()+children.get(2).evaluate();
            case "B" :  return children.get(0).evaluate()-children.get(2).evaluate();
            case "C" : return children.get(0).evaluate()*children.get(2).evaluate();
            case "D" : return (float) Math.cos(children.get(1).evaluate());
            case "E" : return (float) Math.exp(children.get(1).evaluate());
            case "F" : return children.get(1).evaluate();

        }
        return 0;
    }
}
