import java.util.Arrays;
import java.util.List;

/**
 *  Class representing tokens. They are also considered as leaves of parse trees.
 *
 */

public class Token extends Symbol {
    static List<String> tokenNames = Arrays.asList(new String[]{"+","-", "*", "!", "cos", "id", "(",")"});


    @Override
    public String toString() {
        return name;
    }
    Token (String n)
    {
        name=n;
    }

    Token() {
        ;
    }

    @Override
    float evaluate() {
        return 0;
    }
}


