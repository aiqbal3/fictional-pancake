/**
 * MyProfiler.java created by Adeel Iqbal on Macbook Pro in p3b Author: Adeel Iqbal Email:
 * aiqbal3@wisc.edu
 *
 * List collaborators:
 *
 * Other credits:
 * 
 * Known bugs:
 */
/**
 * HashTable.java - This program implements a hash table
 * 
 * @author iqbal (2020)
 *
 */
// Used as the data structure to test our hash table against Tree Map
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {

    HashTableADT<K, V> hashtable;
    TreeMap<K, V> treemap;

    public MyProfiler() {
        // Instantiate your HashTable and Java's TreeMap
      hashtable = new HashTable<K,V>();
      treemap = new TreeMap<K,V>();
      
    }

    /**
     * inserts the key and value into hashtable and treemap
     * @param key - unique key to be inserted
     * @param value - value associated with key, stored together
     */
    public void insert(K key, V value) {
        // Insert K, V into both data structures
      try {
        hashtable.insert(key, value);
        treemap.put(key, value);
      } catch(IllegalNullKeyException exception) {
        exception.printStackTrace();
      } 
    }

    /**
     * gets the value associated with the parameter key from hashtable and treemap
     * @param key - key whose value we want
     */
    public void retrieve(K key) {
      // get value V for key K from data structures
      V hashTableVal;
      V treemapVal;
      try {
        hashTableVal = hashtable.get(key);
        treemapVal = treemap.get(key);
      } catch(IllegalNullKeyException exception) {
        exception.printStackTrace();
      } catch (KeyNotFoundException e) {
        e.printStackTrace();
      }
    }

    public static void main(String[] args) {
        try {
            int numElements = Integer.parseInt(args[0]);

            // Create a profile object.
            // For example, Profile<Integer, Integer> profile = new Profile<Integer,
            // Integer>();
            // execute the insert method of profile as many times as numElements
            // execute the retrieve method of profile as many times as numElements
            // See, ProfileSample.java for example.
            MyProfiler <Integer, Integer> myProfile = new MyProfiler<Integer, Integer>();
            //inserts and retrieves as many times as numElements
            for (int i =0; i<numElements; i++) {
              myProfile.insert(i, i);
              myProfile.retrieve(i);
            }

            String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
            System.out.println(msg);
        } catch (Exception e) {
            System.out.println("Usage: java MyProfiler <number_of_elements>");
            System.exit(1);
        }
    }
}
