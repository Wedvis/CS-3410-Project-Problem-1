package group_project;

import java.util.ArrayList;
import java.util.List;

public class MapManager<T> {
    private StableList<T> objects;
    private CustomHashMap<List<Integer>, AttributeSet> maps;

    public MapManager(StableList<T> objects, CustomHashMap<List<Integer>, AttributeSet> map)
    {
        this.objects = objects;
        this.maps = map;
    }

    public int numObjects()
    {
        return objects.size();
    }

    public MapManager(CustomHashMap<List<Integer>,AttributeSet> map)
    {
        this(new StableList<>(),map);
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
        for(AttributeSet att : attributes.generateSubsets())
        {
            GenKeyVal<List<Integer>,AttributeSet> gKey = maps.get(att);
            List<Integer> attList=null;
            if(gKey!=null)
                attList = gKey.getVal();
            if(attList==null)
            {
                attList = new ArrayList<>();
                maps.put(attList, att);
            }
            attList.add(val);
        }
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

    public void set(int id, AttributeSet attributes,T object)
    {
        removeId(id, attributes);
        pathValue(id, attributes);
        objects.set(id,object);
    }

    //Remind me to save the table layout too
    public String toString()
    {
        return objects.toString();
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
}
