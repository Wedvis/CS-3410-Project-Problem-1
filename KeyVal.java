package group_project;

public class KeyVal<T, U> implements GenKeyVal<T, U> {

    T value;
    U key;


    public KeyVal(T value, U key) {
        this.value = value;
        this.key = key;
    }
    @Override
    public T getVal() {
        return value;
    }

    @Override
    public void setVal(T val) {
        this.value = val;
    }

    @Override
    public U getkey() {
        return key;
    }
}

