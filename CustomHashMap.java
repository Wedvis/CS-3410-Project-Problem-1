package group_project;

public interface CustomHashMap<T, U> {

    void resize(int size);
    int hashfunction(U key);
    int hash2(int key);
    void put(T value, U key);
    GenKeyVal<T, U> get(U key);

}

