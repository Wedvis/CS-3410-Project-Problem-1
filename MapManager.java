package group_project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public class MapManager<T> {
    private TreeSet<String> allAttributes;
    private IdTable<T> objects;
    private CustomHashMap<List<Integer>, AttributeSet> maps;

    public MapManager(IdTable<T> objects, CustomHashMap<List<Integer>, AttributeSet> map)
    {
        this.allAttributes = new TreeSet<>();
        this.objects = objects;
        this.maps = map;
    }

    public int numObjects()
    {
        return objects.size();
    }

    public MapManager(CustomHashMap<List<Integer>,AttributeSet> map)
    {
        this(new IdTable<>(),map);
    }

    //Places object at attribute and assigns id
    public int putObject(T object, AttributeSet attributes)
    {
        int id = objects.add(object);
        pathValue(id,attributes);
        return id;
    }

    //Places id at all attribute subsets
    private void pathValue(int val,AttributeSet attributes)
    {
        allAttributes.addAll(attributes.getAttributes());
        for(AttributeSet att : attributes.generateSubsets())
        {
            // GenKeyVal<List<Integer>,AttributeSet> gKey = maps.get(att);
            // List<Integer> attList=null;
            // if(gKey!=null)
            //     attList = gKey.getVal();
            // if(attList==null)
            // {
            //     attList = new ArrayList<>();
            //     maps.put(attList, att);
            // }
            // attList.add(val);
            directAdd(val, att);
        }
    }

    public void directAdd(int val, AttributeSet attributes)
    {
        GenKeyVal<List<Integer>,AttributeSet> gKey = maps.get(attributes);
            List<Integer> attList=null;
            if(gKey!=null)
                attList = gKey.getVal();
            if(attList==null)
            {
                attList = new ArrayList<>();
                maps.put(attList, attributes);
            }
            attList.add(val);
    }

    public void listAdd(int id,T object)
    {
        objects.set(id, object);
    }

    public void directAddList(Collection<Integer> val, AttributeSet attributes)
    {
        GenKeyVal<List<Integer>,AttributeSet> gKey = maps.get(attributes);
            List<Integer> attList=null;
            if(gKey!=null)
                attList = gKey.getVal();
            if(attList==null)
            {
                attList = new ArrayList<>();
                maps.put(attList, attributes);
            }
            System.out.println(val);
            attList.addAll(val);
    }


    public void removeId(int id, AttributeSet attributes)
    {
        if(objects.remove(id)==null)
            return;
        for(AttributeSet att : attributes.generateSubsets())
        {
            GenKeyVal<List<Integer>,AttributeSet> gKey = maps.get(att);
            List<Integer> attList=null;
            if(gKey!=null)
                attList = gKey.getVal();
            if(attList==null)
                continue;
            attList.remove((Integer)id);
        }
    }

    public T getById(int id)
    {
        return objects.get(id);
    }

    // public void set(int id, AttributeSet attributes,T object)
    // {
    //     removeId(id, attributes);
    //     pathValue(id, attributes);
    //     objects.set(id,object);
    // }
    public void set(int id, AttributeSet oldAttributes, AttributeSet newAttributes, T object)
    {
        if(oldAttributes!=null)
            removeId(id, oldAttributes);
        pathValue(id, newAttributes);
        objects.set(id,object);
    }

    //Remind me to save the table layout too
    public String toString()
    {
        return maps.toString();
    }



    //Gets all ids with attributes
    public List<Integer> getId(AttributeSet attributes)
    {
        GenKeyVal<List<Integer>, AttributeSet> val = maps.get(attributes);
        if(val==null)
            return null;
        return val.getVal();
    }

    //Grabs all cards with attributes
    public List<T> get(AttributeSet attributes)
    {
        List<Integer> id = getId(attributes);
        if(id==null)
            return null;
        List<T> obList = new ArrayList<>();
        for(Integer idInt : id)
        {
            obList.add(objects.get(idInt));
        }
        return obList;
    }

    public Collection<String> getAllAttributes() {
        return allAttributes;
    }
}
