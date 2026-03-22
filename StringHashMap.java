package group_project;

public interface StringHashMap<T> {

    void resize(int size);
    long hashfunction(String key);
    long hash2(String key);
    void put(T value, String key);
    StringKeyVal<T> get(String key);

}
