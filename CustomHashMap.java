package group_project;

public interface CustomHashMap<T, U> {

    void resize(int size);
    long hashfunction(U key);
    long hash2(long key);
    void put(T value, U key);
    GenKeyVal<T, U> get(U key);

}

