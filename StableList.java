package group_project;

import java.util.List;

public class StableList<T>
{
    List<Integer> dataIndex;
    List<Integer> dataId;
    List<T> data;

    public int add(T value)
    {
        if(dataId.size()==data.size())
        {
            dataId.add(data.size());
            dataIndex.add(data.size());
        }
        data.add(value);
        return dataId.get(dataId.size()-1);
    }

    public T get(int id)
    {
        return data.get(dataIndex.get(id));
    }

    public T remove(int id)
    {
        int dIndex = dataIndex.get(id);
        if(dIndex>=data.size())
        {
            return null;
        }
        int lastInd = dataId.get(dataId.size()-1);
        T lastData = data.get(data.size()-1);

        dataId.set(dataId.size()-1,id);
        data.set(dataId.size()-1,data.get(dIndex));
        data.set(dIndex,lastData);
        dataId.set(dIndex,lastInd);
        dataIndex.set(id,dataId.size()-1);
        
        dataIndex.set(lastInd,dIndex);
        return data.remove(data.size()-1);
    }
    public String toString()
    {
        String json = "{[\n";
        for(int i = 0; i<data.size();i++)
        {
            if(i>0)
                json+=",\n";
            json+= String.format("{id:%d,data:%s}",dataId.get(i),data.get(i));
        }
        return json+="]}";
    }
}
