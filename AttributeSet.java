package group_project;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public class AttributeSet
{
    private final TreeSet<String> attributes;

    public AttributeSet(String... attributes)
    {
        this.attributes = new TreeSet<>(Arrays.asList(attributes));
    }

    public AttributeSet(Collection<String> attributes)
    {
        this.attributes = new TreeSet<>(attributes);
    }

    public HashSet<AttributeSet> generateSubsets()
    {
        HashSet<AttributeSet> subsets = new HashSet<>();
        for(HashSet<?> hSet : addSubsets(new HashSet<>(attributes)))
        {
            var sSet = (HashSet<String>)hSet;
            subsets.add(new AttributeSet(sSet));
        }
        subsets.add(this);
        return subsets;
    }

    private HashSet<HashSet<?>> addSubsets(HashSet<?> set)
    {
        HashSet<HashSet<?>> subSets = new HashSet<>();
        for(Object o : set)
        {
            HashSet<?> nextSet = (HashSet<?>)set.clone();
            nextSet.remove(o);
            subSets.add(nextSet);
            subSets.addAll(addSubsets(nextSet));
        }
        return subSets;
    }

    public AttributeSet clone()
    {
        return new AttributeSet((Collection<String>)attributes.clone());
    }

    public boolean equals(Object o)
    {
        if(o instanceof AttributeSet otherSet)
            return otherSet.attributes.equals(attributes);
        return false;
    }
    
    public int hashCode()
    {
        long hash = 1;
        for(String att : attributes)
        {
            hash = hash * 7 + hashString(att);
        }
        return (int)hash;
    }

    public String toString()
    {
        return attributes.toString();
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
    public int hashString(String code)
    {
        return djb2(code);
    }
}
