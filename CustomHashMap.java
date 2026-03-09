package group_project;

public interface CustomHashMap<T> {

    void resize();
    int hashfunction(String key);
    int hash2(String key);
    void put(T value);
    GenKeyVal<T> get(String key);

}
