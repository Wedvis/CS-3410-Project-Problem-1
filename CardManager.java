package group_project;

import java.util.List;

public class CardManager 
{
    private DblTableAutoResize<List<Integer>, AttributeSet> table;
    private MapManager<CardObject> manager;    

    public CardManager(CardObject... cards)
    {
        table = new DblTableAutoResize<>(2, 0.75);
        manager = new MapManager<>(table);
    }

    public int addCard(CardObject cd)
    {
        AttributeSet temp = new AttributeSet(cd.getAtributes());
        int newId = manager.putObject(cd, temp);
        cd.setId(newId);
        return newId;
    }

    public void removeCard(CardObject cd)
    {
        manager.removeId(cd.getId(),new AttributeSet(cd.getAtributes()));
    }

    public List<CardObject> getMatching(AttributeSet attributes)
    {
        return manager.get(attributes);
    }

    public int getNumCards()
    {
        return manager.numObjects();
    }

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
