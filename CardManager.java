package group_project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CardManager 
{
    //Can change, but manager doesn't manage sizing since it is easier to do within the table class
    private DblTableAutoResize<List<Integer>, AttributeSet> table;
    private MapManager<CardObject> manager;
    private DblTableAutoResize<Integer, String> nameTable;

    //Creates a card manager with a 2 power doubling table
    public CardManager()
    {
        table = new DblTableAutoResize<>(2, 0.75);
        manager = new MapManager<>(table);
    }

    public Collection<String> getAllAttributes()
    {
        return manager.getAllAttributes();
    }

    //Adds card to table. I'm lazy so it uses set if keepid; add and sets to newId otherwise
    public int addCard(CardObject cd, boolean keepId)
    {
        AttributeSet temp = new AttributeSet(cd.getAtributes());
        if(keepId)
        {
            CardObject old = removeById(cd.getId());
            AttributeSet oldAttr = null;
            if(old!=null)
                oldAttr = new AttributeSet(old.getAtributes());
            manager.set(cd.getId(),oldAttr,temp,cd);
            return cd.getId();
        }
        int newId = manager.putObject(cd, temp);
        cd.setId(newId);
        return newId;
    }

    //Removes card with id provided
    public void removeCard(CardObject cd)
    {
        manager.removeId(cd.getId(),new AttributeSet(cd.getAtributes()));
    }

    public CardObject removeById(int id)
    {
        CardObject card = manager.getById(id);
        if(card==null)
            return null;
        assert(card.getId()==id);
        manager.removeId(card.getId(), new AttributeSet(card.getAtributes()));
        return card;
    }

    //Grabs all Card Objects matching attributes provided
    public List<CardObject> getMatching(AttributeSet attributes)
    {
        //If attributes is null, get all cards
        if(attributes==null)
            attributes = new AttributeSet(new ArrayList<>());
        return manager.get(attributes);
    }

    public int getNumCards()
    {
        return manager.numObjects();
    }

    //Adds multiple cards to db
    public void addMultiple(boolean keepId, CardObject... cards)
    {
        for(CardObject c : cards)
        {
            addCard(c,keepId);
        }
    }

    public void listDirectAdd(CardObject cd)
    {
        manager.listAdd(cd.getId(), cd);
    }

    public void applyPremadeTable(String tblStr, Collection<CardObject> cards)
    {
        applyTableString(tblStr);
        for(CardObject cd : cards)
            listDirectAdd(cd);
    }

    //Returns map manager toString
    public String toString()
    {
        String temp = manager.toString();
        return temp;
    }

    public void addMultiple(boolean keepId, Collection<CardObject> cards)
    {
        addMultiple(keepId, (CardObject[])cards.toArray());
    }

    //Call before Adding Cards
    public void applyTableString(String table)
    {
        String[] entries = table.split(";\n");
        for(String str : entries)
        {
            System.out.println(str);
            String[] keyVal = str.split("##");
            System.out.println(keyVal[0] + "----" + keyVal[1]);
            HashSet<String> attrKey = new HashSet<>();
            if(keyVal[0].length()>2)
                for(String attr : keyVal[0].substring(1,keyVal[0].length()-1).split(", "))
                {
                    // System.out.println(attr);
                    attrKey.add(attr);
                }
            ArrayList<Integer> ids = new ArrayList<>();
            System.out.println(keyVal[1]+"\n");
            if(keyVal[1].length()>2)
                for(String id : keyVal[1].substring(1,keyVal[1].length()-1).split(", "))
                {
                    ids.add(Integer.parseInt(id));
                }
            manager.directAddList(ids, new AttributeSet(attrKey));
        }
    } 

    //Just a lazy way to manage table sizing
    private class DblTableAutoResize<T,U> extends DblHashMap_HashCode<T, U>
    {
        private int size;
        private double loadFactor;
        public DblTableAutoResize(int size,double loadFactor)
        {
            super(size);
            this.size = size;
            this.loadFactor = loadFactor;
        }

        //Just doubles table once beyond load factor
        @Override
        public void put(T value, U key) {
            super.put(value, key);
            if(valCount<size*loadFactor)
                return;
            size = size*2;
            resize(size);
        }
        public String toString()
        {
            String msg = "";
            for(GenKeyVal<T,U> k : array)
            {
                if(k==null || k.getVal()==null)
                    continue;
                msg+= k.getkey() + "##" + k.getVal() + ";\n";
            }
            return msg;
        }
    }
}
