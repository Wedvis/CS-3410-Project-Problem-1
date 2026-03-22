package group_project;

import java.util.ArrayList;
import java.util.List;

public class StableList<T>
{
    List<Integer> dataIndex;
    List<Integer> dataId;
    List<T> data;

    public StableList()
    {
        this.dataIndex = new ArrayList<>();
        this.dataId = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    public int size()
    {
        return data.size();
    }

    public int add(T value)
    {
        if(dataId.size()==data.size())
        {
            dataId.add(data.size());
            dataIndex.add(data.size());
        }
        data.add(value);
        return dataId.get(data.size()-1);
    }

    public T get(int id)
    {
        return data.get(dataIndex.get(id));
    }
    public void set(int id, T obj)
    {
        if(id>=dataIndex.size())
        {
            int currSize = dataIndex.size();
            for(int i = 0; i<=id-currSize;i++)
            {
                dataIndex.add(data.size()+i);
                dataId.add(data.size()+i);
            }
        }
        if(dataIndex.get(id)>=data.size())
        {
            dataIndex.set(id,data.size());
            dataId.set(data.size(),id);
            dataIndex.set(data.size(),id);
            dataId.set(id,data.size());
            data.add(obj);
            return;
        }
        data.set(dataIndex.get(id),obj);
    }
    public T remove(int id)
    {
        if(id>=dataIndex.size())
            return null;
        int dIndex = dataIndex.get(id);
        if(dIndex>=data.size())
            return null;
        if(data.size()-1==dIndex)
            return data.removeLast();
        T remObj = data.get(dIndex);
        int lastId = dataId.get(data.size()-1);

        //Set id pos to last data
        dataIndex.set(id,data.size()-1);
        //Set last id to removal index
        dataIndex.set(lastId,dIndex);

        //Set removed location to
        data.set(dIndex,data.removeLast());
        dataId.set(dIndex,lastId);
        return remObj;
    }
    //Should be proper json, assuming item type is proper json
    public String toString()
    {
        String json = "{[\n";
        for(int i = 0; i<data.size();i++)
        {
            if(i>0)
                json+=",\n";
            json+= String.format("{id:%d,data:{%s}}",dataId.get(i),data.get(i));
        }
        return json+="]}";
    }
}
