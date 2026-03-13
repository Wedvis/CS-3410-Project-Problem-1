package group_project;

import java.security.Key;

public class DblHashMap<T> implements CustomHashMap<T> {

    public KeyVal<T>[] array;
    public int valCount = 0;

    public DblHashMap(int size) {
        array = new KeyVal[size];
    }

    //Resize function. Takes all of the values in the HashMap and throws them into a temp array, increases the size
    //of the internal array, and then uses put to place everything back into the array.
    //No logic for when to increase size, use manager class to implement that.
    @Override
    public void resize(int size) {
        KeyVal<T>[] tempArray = new KeyVal[valCount];
        int j = 0;

        for(int i = 0; i < array.length; ++i) {
            if(array[i] != null) {
                tempArray[j] =  array[i];
                j++;
            }
        }

        array = new KeyVal[size];

        for(int i = 0; i < tempArray.length; i++) {
            put(tempArray[i].getVal(), tempArray[i].getkey());
        }
    }

    //primary hash function, uses djb2 algorithm for hashing with high entropy large numbers
    //along with prime number multiplication
    @Override
    public int hashfunction(String key) {
        char[] carr = key.toCharArray();
        int jbCode = 1;
        for(char c : carr)
        {
            jbCode = jbCode * 7 + c;
        }
        return jbCode;
    }

    //Secondary hash function, also uses djb2, multiplies by a different prime number.
    //NOTE: both of these hash functions can easily integer overflow if not handled correctly, potentially
    //change return type to (long)
    @Override
    public int hash2(String key) {
        char[] carr = key.toCharArray();
        int jbCode = 1;
        for(char c : carr)
        {
            jbCode = jbCode * 11 + c;
        }
        return jbCode;
    }

    //Helper for put, probes for next spot to place the value. Does so by using secondary hash function,
    //and then adding 1 for every subsequent position in the array, to scan the whole array for a place.
    private void probe(String key, KeyVal<T> keyval) {
        int newHash = hash2(key);
        boolean noPlace = true;

        for(int i = 0; i < array.length; i++) {
            int index = (newHash + i) % array.length;


            if(array[index] == null) {
                array[index] = keyval;
                valCount += 1;
                noPlace = false;
                break;
            }
            else if(key.equals(array[index].getkey())){
                array[index] = keyval;
                noPlace = false;
                break;
            }

        }

        if(noPlace) {
            System.out.println("No position for the key to go");
        }
    }

    //Helper for get, probes just like the probe function, but tries to find the key instead. If not found,
    //returns -1
    private int find(String key) {

        int newHash = hash2(key);


        for(int i = 0; i < array.length; i++) {
            int index = (newHash + i) % array.length;

            if(array[index] != null) {
                if(key.equals(array[index].getkey())){
                    return index;
                }
            }

        }

        return -1;
    }

    //Put function, uses first hash function to hash key into a position. If that position is has something,
    //Check if it the keys are equal. If keys are equal, replace value, if keys are not equal, start probing.
    @Override
    public void put(T value, String key) {
        KeyVal<T> keyval = new KeyVal(value, key);
        int index = hashfunction(key) % array.length;
        System.out.println(hashfunction(key));

        if(array[index] == null) {
            array[index] = keyval;
            valCount += 1;
        }
        else {
            if(key.equals(array[index].getkey())) {
                array[index] = keyval;
            }
            else {
                probe(key, keyval);
            }
        }
    }

    //get function, IMPORTANT: will produce null valueif key is not found, either initially or through using
    //find. Make sure the manager is able to account for the null values.
    @Override
    public GenKeyVal<T> get(String key) {
        KeyVal<T> keyval = array[hashfunction(key) % array.length];

        if(keyval == null) {
            return null;
        }
        else if(keyval.getkey().equals(key)) {
            return keyval;
        }
        else {
            int newIndex = find(key);
            if(newIndex != -1) {
                return array[newIndex];
            }
            else {
                System.out.println("key not found");
                return null;
            }
        }
    }
}
