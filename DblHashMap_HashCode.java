package group_project;

public class DblHashMap_HashCode<T, U> implements CustomHashMap<T, U>{
    public KeyVal<T, U>[] array;
    public int valCount = 0;
    public boolean isResize = false;

    public DblHashMap_HashCode(int size) {
        array = new KeyVal[size];
    }

    //Resize function. Takes all of the values in the HashMap and throws them into a temp array, increases the size
    //of the internal array, and then uses put to place everything back into the array.
    //No logic for when to increase size, use manager class to implement that.

    public void resize(int size) {
        isResize = true;
        KeyVal<T, U>[] tempArray = new KeyVal[valCount];
        int j = 0;

        for(int i = 0; i < array.length; ++i) {
            if(array[i] != null && array[i].getVal()!=null) {
                tempArray[j] =  array[i];
                j++;
            }
        }

        array = new KeyVal[size];

        int newCount = 0;
        for(int i = 0; i < tempArray.length; i++) {
            if(tempArray[i]==null)
                break;
            put(tempArray[i].getVal(), tempArray[i].getkey());
            newCount++;
        }
        isResize = false;
        valCount = newCount;
    }

    //primary hash function, uses djb2 algorithm for hashing with high entropy large numbers
    //along with prime number multiplication

    public long hashfunction(U key) {
        if(key instanceof Number k)
            return hash2(k.longValue());
        int jbCode = key.hashCode();
        if(jbCode < 0) {
            return jbCode * -1;
        }
        return jbCode;
    }

    //Secondary hash function, turns integer into a string, reverses it, and turns back into
    //an integer

    public long hash2(long key) {
        long hash = HashFunctions.javaIntRandomize(key);
        if(hash < 0) {
            hash *= -1;
        }

        return hash;
    }

    //Helper for put, probes for next spot to place the value. Does so by using secondary hash function,
    //and then adding 1 for every subsequent position in the array, to scan the whole array for a place.
    private void probe(long key, KeyVal<T, U> keyval) {
        long newHash = hash2(key);
        boolean noPlace = true;

        for(int i = 0; i < array.length; i++) {
            int index = (int)((newHash + i) % array.length);


            if(array[index] == null) {
                array[index] = keyval;
                if(!isResize) {
                    valCount += 1;
                }
                noPlace = false;
                break;
            }
            else if(keyval.getkey().equals(array[index].getkey())){
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
    private int find(U key) {

        long newHash = hash2(hashfunction(key));


        for(int i = 0; i < array.length; i++) {
            int index = (int)((newHash + i) % array.length);

            if(array[index] == null) {
                break;
            }
            if(key.equals(array[index].getkey())){
                return index;
            }

        }

        return -1;
    }

    //Put function, uses first hash function to hash key into a position. If that position has something,
    //check if the keys are equal. If keys are equal, replace value, if keys are not equal, start probing.

    public void put(T value, U key) {
        KeyVal<T, U> keyval = new KeyVal(value, key);
        int index = (int)(hashfunction(key) % array.length);


        if(array[index] == null) {
            array[index] = keyval;
            if(!isResize) {
                valCount += 1;
            }
        }
        else {
            if(key.equals(array[index].getkey())) {
                array[index] = keyval;
            }
            else {
                probe(hashfunction(key), keyval);
            }
        }
    }

    //get function, IMPORTANT: will produce null value if key is not found, either initially or through using
    //find. Make sure the manager is able to account for the null values.

    public GenKeyVal<T, U> get(U key) {
        KeyVal<T, U> keyval = array[(int) (hashfunction(key) % array.length)];

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
