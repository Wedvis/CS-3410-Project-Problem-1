package group_project;

public class KeyVal<T> implements GenKeyVal<T> {

    T value;
    String key;


    public KeyVal(T value, String key) {
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
    public String getkey() {
        return key;
    }
}
