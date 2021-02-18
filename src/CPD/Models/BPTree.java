package CPD.Models;

import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class BPTree<Key extends Comparable<Key>> implements java.io.Serializable{
    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    public static final int M = 100;

    public Node root;       // root of the B-tree
    public int height;      // height of the B-tree
    public int N;           // number of key-value pairs in the B-tree

    // helper B-tree node data type
    public static final class Node implements java.io.Serializable {
        public int m;                             // number of children
        public Entry[] children = new Entry[M];   // the array of children

        // create a node with k children
        public Node(int k) {
            m = k;
        }
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    public static class Entry  implements java.io.Serializable{
        public Comparable key;
        public List<Integer> val;
        public Node next;     // helper field to iterate over array entries

        public Entry(Comparable key, List<Integer> val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Initializes an empty B-tree.
     */
    public BPTree() {
        root = new Node(0);
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return <tt>true</tt> if this symbol table is empty; <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return N;
    }

    /**
     * Returns the height of this B-tree (for debugging).
     *
     * @return the height of this B-tree
     */
    public int height() {
        return height;
    }


    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and <tt>null</tt> if the key is not in the symbol table
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public ArrayList get(Key key) {
        if (key == null) throw new NullPointerException("key must not be null");
        return search(root, key, height);
    }

    public ArrayList search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) return (ArrayList) children[j].val;
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.m; j++) {
                if (j + 1 == x.m || less(key, children[j + 1].key))
                    return search(children[j].next, key, ht - 1);
            }
        }
        return null;
    }


    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is <tt>null</tt>, this effectively deletes the key from the symbol table.
     *
     * @param key the key
     * @param val the value
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public void put(Key key, List<Integer> val) {
        if (key == null) throw new NullPointerException("key must not be null");
        Node u = insert(root, key, val, height);
        N++;
        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    public Node insert(Node h, Key key, List<Integer> val, int ht) {
        int j;
        Entry t = new Entry(key, val, null);

        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        }

        // internal node
        else {
            for (j = 0; j < h.m; j++) {
                if ((j + 1 == h.m) || less(key, h.children[j + 1].key)) {
                    Node u = insert(h.children[j++].next, key, val, ht - 1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--)
            h.children[i] = h.children[i - 1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else return split(h);
    }

    // split node in half
    public Node split(Node h) {
        Node t = new Node(M / 2);
        h.m = M / 2;
        for (int j = 0; j < M / 2; j++)
            t.children[j] = h.children[M / 2 + j];
        return t;
    }

    /**
     * Returns a string representation of this B-tree (for debugging).
     *
     * @return a string representation of this B-tree.

    public String toString() {
    return toString(root, height, "") + "\n";
    }

    public String toString(Node h, int ht, String indent) {
    StringBuilder s = new StringBuilder();
    Entry[] children = h.children;

    if (ht == 0) {
    for (int j = 0; j < h.m; j++) {
    s.append(indent + children[j].key + children[j].val.toString() + "\n");
    }
    } else {
    for (int j = 0; j < h.m; j++) {
    if (j > 0) s.append(indent + "(" + children[j].key + ")\n");
    s.append(toString(children[j].next, ht - 1, indent + "     "));
    }
    }
    return s.toString();
    } */

    public String toString() {

        return toString(root, height);
    }

    public String toString(Node h, int ht) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s.append(children[j].key + "\n");
                for (Integer cat: children[j].val){
                    try {
                        FileInputStream fileIn = new FileInputStream("mainFile.ser");
                        Question question = null;
                        fileIn.skip(cat);
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        question = (Question) in.readObject();
                        s.append("    " + question.question + "\n");

                    }catch(Exception e){
                        System.out.println("aa");
                    }

                }
            }
        } else {
            for (int j = 0; j < h.m; j++) {
                // if (j > 0) s.append(children[j].key + "\n");
                s.append(toString(children[j].next, ht - 1));
            }
        }

        return s.toString();
    }


    // comparison functions - make Comparable instead of Key to avoid casts
    public boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    public boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
}
