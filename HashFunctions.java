package group_project;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashFunction;
//NOTE, to use the murmurhash3, you need to install the guava-33.5.0-jre.jar file, which gives you the guava
//hashing libraries.
public class HashFunctions {

    //every hash produces a long data type, to use in a bucket, modulo the value by table size and then typecaste
    //to int using (int)
    //ex: int bucket = (int)(hash % tableSize);
    //None of these hash functions were made by us, they are just widely used hash functions which we did research on and implemented.
    
    public static long murmurhash3(String key) {
        HashFunction murmurHash3_32_1 = Hashing.murmur3_32_fixed(42);
        int hash = murmurHash3_32_1.newHasher().putString(key, java.nio.charset.StandardCharsets.UTF_8).hash().asInt();

        return Integer.toUnsignedLong(hash);
    }

    public static long murmurhash3_dbl(String key) {
        HashFunction murmurHash3_32_1 = Hashing.murmur3_32_fixed(69);
        int hash = murmurHash3_32_1.newHasher().putString(key, java.nio.charset.StandardCharsets.UTF_8).hash().asInt();

        return Integer.toUnsignedLong(hash);
    }

    public static long fnv1a(String key) {
        final long FNV_PRIME = 0x01000193;  //Hexadecimal for 16777619
        final long FNV_OFFSET = 0x811c9dc5L; //Hexadecimal for 2166136261

        long hash = FNV_OFFSET;
        char[] carr = key.toCharArray();
        for(char c: carr) {
            hash ^= c;
            hash *= FNV_PRIME;
        }

        return hash;
    }

    public static long fnv1(String key) {
        final long FNV_PRIME = 0x01000193;  //Hexadecimal for 16777619
        final long FNV_OFFSET = 0x811c9dc5L; //Hexadecimal for 2166136261

        long hash = FNV_OFFSET;
        char[] carr = key.toCharArray();
        for(char c: carr) {
            hash *= FNV_PRIME;
            hash ^= c;
        }

        return hash;
    }

    public static long djb2(String key) {
        long hash = 5381;
        char[] carr = key.toCharArray();

        for(char c: carr) {
            hash = hash * 33 + c;
        }

        return hash;
    }

    public static long djb2_dbl(String key) {
        long hash = 5381;
        char[] carr = key.toCharArray();
        for(char c: carr) {
            hash = hash * 33 + c;
        }

        return hash;
    }

    //https://rosettacode.org/wiki/Pseudo-random_numbers/Splitmix64
    public static long javaIntRandomize(long key) {
        key += 0x9e3779b97f4a7c15L;
        key = (key ^ (key >>> 30)) * 0xbf58476d1ce4e5b9L;
        key = (key ^ (key >>> 27)) * 0x94d049bb133111ebL;
        return key ^ (key >>> 31);
    }

    public static long javaHashCode(String key) {
        return key.hashCode();
    }
}
