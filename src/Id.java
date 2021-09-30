/**
 * Tokens for Identifiers. They also store the values of floating point numbers.
 */

public class Id extends Token
{
    String attribute;
    Id (String n)
    {
        super.name="id";
        attribute=n;
    }
    @Override
    public String toString() {
        if (!name.equals("id")) throw new UnsupportedOperationException("wrong token format");
        return attribute;
    }

    @Override
    float evaluate() {
        return Float.parseFloat(attribute);
    }
}
