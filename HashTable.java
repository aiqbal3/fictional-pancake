/**
 * HashTable.java created by Adeel Iqbal on Macbook Pro in p3a Author: Adeel Iqbal Email:
 * aiqbal3@wisc.edu
 *
 * Lecture: 001 Course: CS400 Semester: Spring 2020
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
import java.util.ArrayList;

// TODO: comment and complete your HashTableADT implementation
// DO ADD UNIMPLEMENTED PUBLIC METHODS FROM HashTableADT and DataStructureADT TO YOUR CLASS
// DO IMPLEMENT THE PUBLIC CONSTRUCTORS STARTED
// DO NOT ADD OTHER PUBLIC MEMBERS (fields or methods) TO YOUR CLASS
//
// TODO: implement all required methods
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//      bucket
//
// TODO: explain your hashing algorithm here
//       using linked nodes to create hashtable, array of linked nodes, chaining
// NOTE: you are not required to design your own algorithm for hashing,
// since you do not know the type for K,
// you must use the hashCode provided by the <K key> object
// and one of the techniques presented in lecture
//
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
  private int numKeys;
  private int capacity;
  private double lFThreshold;
  private ArrayList<Node> list;
  private double loadFactor;

  private class Node {
    private K key;
    private V value;
    private Node next;

    private Node(K k, V v) {
      key = k;
      value = v;
    }

    private K getKey() {
      return key;
    }

    private V getValue() {
      return value;
    }
  }

  // TODO: comment and complete a default no-arg constructor
  public HashTable() {
    list = new ArrayList<>();
    lFThreshold = 0.75;
    capacity = 13;
    for (int i = 0; i < capacity; i++) {
      list.add(null);// setting elements to null
    }
  }

  // initial capacity and load factor threshold
  // threshold is the load factor that causes a resize and rehash
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    capacity = initialCapacity;
    lFThreshold = loadFactorThreshold;
    list = new ArrayList<>();
    for (int i = 0; i < capacity; i++) {
      list.add(null);// setting elements to null
    }
  }

  /**
   * this method is used to get the hash index
   * 
   * @param key
   * @return - key's index in the hash table
   */
  private int getHashIndex(K key) {
    return key.hashCode() % capacity;
  }
  
  /**
   * inserts the key and value into hashtable
   * @param key - key to insert
   * @param value - value to insert
   * @throws illegalNullKeyException if key is null
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException("null key");
    }

    int hashIndex = getHashIndex(key);

    Node tempNode = list.get(hashIndex);

    boolean found = false;

    while (tempNode != null) {
      if (tempNode.key.equals(key)) { // duplicate key case
        found = true;
        return;
      } else {
        tempNode = tempNode.next; // if key not found, move to next
      }
    } // end while

    if (found) {// if duplicate key was found, numkeys does not change
      Node dupKey = list.get(key.hashCode());
      if (dupKey.next.key.equals(key)) {
        V dupNewVal = dupKey.value;
      }
      while (dupKey.next.next != null && !dupKey.next.next.key.equals(key)) {
        dupKey = tempNode.next;
      }
      dupKey.value = value;// updates value for duplicate key
    } else {
      numKeys++;
      tempNode = list.get(hashIndex);

      Node newNode = new Node(key, value);
      newNode.next = tempNode;

      list.set(hashIndex, newNode);
      if (getLoadFactor() > getLoadFactorThreshold()) {
        ArrayList<Node> origList = list;
        resize(origList);
      }
    } // end else
  }

  /**
   * doubles the size of original list, and adds 1 to make capacity prime num
   * 
   * @param origList - the list we want to resize
   */
  public void resize(ArrayList<Node> origList) {
    list = new ArrayList<>();
    capacity = capacity * 2 + 1;
    for (int i = 0; i < capacity; i++) {
      list.add(null);
    }
    for (Node copyNode : origList) {// copying data after resize
      while (copyNode != null) {
        try {
          insert(copyNode.key, copyNode.value);
        } catch (IllegalNullKeyException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        copyNode = copyNode.next;// update pointer to next node
      }
    }
  }

  /**
   * // If key is found, // remove the key,value pair from the data structure // decrease number of
   * keys. // return true // If key is null, throw IllegalNullKeyException // If key is not found,
   * return false
   * @throws - illegalNullKeyException if key is null
   * @param key - the key to remove
   * @return - true if removed sucessfully, false otherwise
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    // TODO Auto-generated method stub
    if (key == null) {
      throw new IllegalNullKeyException("Null key cannot be removed");
    }

    int index = getHashIndex(key);
    Node tempNode = list.get(index);
    boolean found = false;
    Node prevNode = null;

    while (tempNode != null) {
      if (tempNode.key.equals(key)) {
        found = true;
        break;
      }
      prevNode = tempNode;
      tempNode = tempNode.next;
    }

    if (tempNode == null) {
      return false;
    }

    numKeys--;// reduce number of keys after removing

    if (prevNode != null) {
      prevNode.next = tempNode.next;

    } else {
      list.set(index, tempNode.next);
      return true;
    }
    return true;
  }

  /**
   * returns the value associated with the key without removing
   * @param Key - the key whose value we want
   * @return - the value associated with key
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalNullKeyException("Key is null");
    }

    int hashIndex = getHashIndex(key);
    Node tempNode = list.get(hashIndex);
    boolean foundKey = false;

    while (tempNode != null) {
      if (tempNode.key.equals(key)) {
        foundKey = true;
        return tempNode.value;
      } else {
        tempNode = tempNode.next;
      }
    } // end while
    if (foundKey == false) {// if not found
      throw new KeyNotFoundException("Key could not be found");
    }
    return null;
  }

  /**
   * returns the number of keys in hashtable
   */
  @Override
  public int numKeys() {
    return numKeys;
  }

  /**
   * returnds the load factor threshold for the hashtable
   */
  @Override
  public double getLoadFactorThreshold() {
    return lFThreshold;
  }

  /**
   * calculates load factor and returns it
   */
  @Override
  public double getLoadFactor() {
    return numKeys / capacity; // loadFactor = numKeys / capacity
  }

  /**
   * returns the capacity of hashtable
   */
  @Override
  public int getCapacity() {
    return capacity;
  }

  /**
   * returns 5 as collision resolution
   */
  @Override
  public int getCollisionResolution() {
    return 5;
  }

}
