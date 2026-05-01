package group_project;

public class StringKeyVal<T> {

    T value;
    String key;



    public StringKeyVal(T value, String key) {
        this.value = value;
        this.key = key;
    }

    public T getVal() {
        return value;
    }


    public void setVal(T val) {
        this.value = val;
    }


    public String getkey() {
        return key;
    }
}
