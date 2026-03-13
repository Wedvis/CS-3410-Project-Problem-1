package group_project;

public interface CustomHashMap<T> {

    void resize(int size);
    int hashfunction(String key);
    int hash2(String key);
    void put(T value, String key);
    GenKeyVal<T> get(String key);
}

