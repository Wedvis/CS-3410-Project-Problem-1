package group_project;

import java.util.Stack;
public class IdTable<T>
{
    int size;
    private DblTableAutoResize<T,Integer> table;
    private Stack<Integer> freedIds;
    int nextId;
    public IdTable()
    {
        this.table = new DblTableAutoResize<>(2,0.75);
        this.freedIds = new Stack<>();
        nextId = 0;
        size=0;
    }

    public T get(int id)
    {
        GenKeyVal<T,Integer> keyVal = table.get(id);
        if(keyVal==null)
            return null;
        return keyVal.getVal();
    }
    public int add(T object)
    {
        int id = nextId;
        if(!freedIds.isEmpty())
            id=freedIds.pop();
        else
            nextId++;
        table.put(object, id);
        size++;
        return id;
    }

    public T remove(int id)
    {
        T obj = get(id);
        if(obj==null)
            return null;
        table.put(null,id);
        size--;
        return obj;
    }

    public void set(int id, T obj)
    {
        if(get(id)==null)
            size++;
        table.put(obj,id);
        freedIds.remove((Integer)id);
        if(id<nextId)
            return;
        for(int i = nextId; i<id;i++)
        {
            freedIds.push(i);
        }
        nextId=id+1;
    }

    public int size()
    {
        return size;
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