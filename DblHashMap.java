package group_project;


public class DblHashMap<T> implements StringHashMap<T> {

    public StringKeyVal<T>[] array;
    public int valCount = 0;
    public boolean isResize = false;
    public int collisions = 0;
    public int probes_put = 0;
    public int gets = 0;
    public int probes_find = 0;
    public int finds_occuring = 0;

    public DblHashMap(int size) {
        array = new StringKeyVal[size];
    }


    //Resize function. Takes all of the values in the HashMap and throws them into a temp array, increases the size
    //of the internal array, and then uses put to place everything back into the array.
    //No logic for when to increase size, use manager class to implement that.
    @Override
    public void resize(int size) {
        isResize = true;
        StringKeyVal<T>[] tempArray = new StringKeyVal[valCount];
        int j = 0;

        for(int i = 0; i < array.length; ++i) {
            if(array[i] != null && array[i].getVal()!=null) {
                tempArray[j] =  array[i];
                j++;
            }
        }

        array = new StringKeyVal[size];

        for(int i = 0; i < tempArray.length; i++) {
            if(tempArray[i]==null)
                break;
            put(tempArray[i].getVal(), tempArray[i].getkey());
        }
        isResize = false;
    }

    public void resizePrime() {
        isResize = true;
        int newSize = array.length * 2;

        while(!isPrime(newSize)) {
            ++newSize;
        }

        StringKeyVal<T>[] tempArray = new StringKeyVal[valCount];
        int j = 0;

        for(int i = 0; i < array.length; ++i) {
            if(array[i] != null && array[i].getVal()!=null) {
                tempArray[j] =  array[i];
                j++;
            }
        }

        array = new StringKeyVal[newSize];

        for(int i = 0; i < tempArray.length; i++) {
            if(tempArray[i]==null)
                break;
            put(tempArray[i].getVal(), tempArray[i].getkey());
        }


        isResize = false;
    }

    //primary hash function, uses djb2 algorithm for hashing with high entropy large numbers
    //along with prime number multiplication
    @Override
    public long hashfunction(String key) {
        HashFunctions hf = new HashFunctions();
        long hash = hf.djb2(key);
        if(hash < 0) {
            return -1 * hash;
        }
        return hash;
    }

    //Secondary hash function, also uses djb2, multiplies by a different prime number.
    //NOTE: both of these hash functions can easily integer overflow if not handled correctly, potentially
    //change return type to (long)
    @Override
    public long hash2(String key) {
       HashFunctions hf = new HashFunctions();
       //long hash = hashfunction(key);
       long hash = hf.djb2_dbl(key);
        if(hash < 0) {
            return -1 * hash;
        }
       return hash;
    }

    //Helper for put, probes for next spot to place the value. Does so by using secondary hash function,
    //and then adding 1 for every subsequent position in the array, to scan the whole array for a place.
    private void probe(String key, StringKeyVal<T> keyval) {
        long newHash = 17 - (hash2(key) % 17);
        if(newHash == array.length) {
            newHash -= 1;
        }
        long oldHash = hashfunction(key);

//        long newHash = hash2(key);
        boolean noPlace = true;

        for(int i = 1; i < array.length; i++) {
            int index = ((int)((oldHash + (i * newHash)) % array.length));
            if(!isResize) {
                ++probes_put;
            }

//            int index = ((int)((newHash + i) % array.length));
            //Replace above line of code with commented line if you want to use non prime numbers for table size


            if(array[index] == null) {
                array[index] = keyval;
                if(!isResize) {
                    valCount += 1;
                }
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
            System.out.println("No position for " + key + " to go, table size: " + array.length);
        }
    }

    //Helper for get, probes just like the probe function, but tries to find the key instead. If not found,
    //returns -1
    private int find(String key) {
        ++finds_occuring;
        long newHash = 17 - (hash2(key) % 17);
        if(newHash == array.length) {
            newHash -= 1;
        }
//        long newHash = hash2(key);
        long oldHash = hashfunction(key);

        for(int i = 1; i < array.length; i++) {
            int index = ((int)((oldHash + (i * newHash)) % array.length));
            ++gets;
            ++probes_find;
//            int index = ((int)((newHash + i) % array.length));
            //replace above code with commented line if you want to use non-prime table size.

            if(array[index] != null) {
                if(key.equals(array[index].getkey())){
                    return index;
                }
            }

        }

        return -1;
    }

    public StringKeyVal<T> remove(String key) {
        int index = (int)(hashfunction(key) % array.length);
        StringKeyVal<T> keyval = array[index];

        if(array[index] == null) {
            return null;
        }
        if (keyval.getkey().equals(key)) {
            StringKeyVal<T> newkeyval = array[index];
            array[index] = new StringKeyVal<>(null, key);
            return newkeyval;
        }
        else {
            int newIndex = find(key);
            if(newIndex != -1) {
                StringKeyVal<T> newkeyval = array[newIndex];
                array[newIndex] = new StringKeyVal<>(null, key);
                return newkeyval;
            }
            else {
                System.out.println("key not found");
                return null;
            }
        }

    }

    //Put function, uses first hash function to hash key into a position. If that position is has something,
    //Check if it the keys are equal. If keys are equal, replace value, if keys are not equal, start probing.
    @Override
    public void put(T value, String key) {
        StringKeyVal<T> keyval = new StringKeyVal(value, key);
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
                probe(key, keyval);
                if(!isResize) {
                    collisions += 1;
                }
            }

        }
    }

    //get function, IMPORTANT: will produce null valueif key is not found, either initially or through using
    //find. Make sure the manager is able to account for the null values.
    @Override
    public StringKeyVal<T> get(String key) {
        StringKeyVal<T> keyval = array[(int)(hashfunction(key) % array.length)];
        ++gets;

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

    //Found this algorithm for finding prime numbers from
    // https://www.geeksforgeeks.org/java/java-prime-number-program/

    private boolean isPrime(int n) {
        // Corner case
        if (n <= 1)
            return false;
        // For n=2 or n=3 it will check
        if (n == 2 || n == 3)
            return true;
        // For multiple of 2 or 3 This will check
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        // It will check all the others condition
        for (int i = 5; i <= Math.sqrt(n); i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }
}
