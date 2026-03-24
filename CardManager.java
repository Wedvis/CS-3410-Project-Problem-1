package group_project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CardManager 
{
    //Can change, but manager doesn't manage sizing since it is easier to do within the table class
    private DblTableAutoResize<List<Integer>, AttributeSet> table;
    private MapManager<CardObject> manager;    

    //Creates a card manager with a 2 power doubling table
    public CardManager()
    {
        table = new DblTableAutoResize<>(2, 0.75);
        manager = new MapManager<>(table);
    }

    //Adds card to table. I'm lazy so it uses set if keepid; add and sets to newId otherwise
    public int addCard(CardObject cd, boolean keepId)
    {
        AttributeSet temp = new AttributeSet(cd.getAtributes());
        if(keepId)
        {
            manager.set(cd.getId(),temp,cd);
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
    }
}
