
import java.util.Arrays;
import java.util.TreeSet;

public class AttributeSet
{
    private final TreeSet<String> attributes;

    public AttributeSet(String... attributes)
    {
        this.attributes = new TreeSet<>(Arrays.asList(attributes));
    }

    public boolean equals(Object o)
    {
        if(o instanceof AttributeSet otherSet)
            return otherSet.attributes.equals(attributes);
        return false;
    }
    
    public int hashCode()
    {
        int hash = 1;
        for(String att : attributes)
        {
            hash = hash * 7 + djb2(att);
        }
        return hash;
    }

    public static int djb2(String code)
    {
        char[] carr = code.toCharArray();
        int jbCode = 1;
        for(char c : carr)
        {
            jbCode = jbCode * 31 + c;
        }
        return jbCode;
    }
}
