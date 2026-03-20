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

    public MapManager(CustomHashMap<List<Integer>,AttributeSet> map)
    {
        this(new StableList<>(),map);
    }

    public void putObject(T object, AttributeSet attributes)
    {
        int id = objects.add(object);
        pathValue(id,attributes);
    }

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

    public List<Integer> getId(AttributeSet attributes)
    {
        GenKeyVal<List<Integer>, AttributeSet> val = maps.get(attributes);
        if(val==null)
            return null;
        return val.getVal();
    }

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
