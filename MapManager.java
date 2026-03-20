package group_project;

import java.util.ArrayList;
import java.util.List;

public class MapManager<T> {
    StableList<T> objects;
    CustomHashMap<List<Integer>, AttributeSet> maps;

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
        return maps.get(attributes).getVal();
    }

    public List<T> get(AttributeSet attributes)
    {
        List<Integer> id = getId(attributes);
        List<T> obList = new ArrayList<>();
        for(Integer idInt : id)
        {
            obList.add(objects.get(idInt));
        }
        return obList;
    }
}
