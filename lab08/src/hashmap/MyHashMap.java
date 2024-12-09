package hashmap;

import java.util.*;
import java.util.function.Consumer;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Collection<Node>[] buckets;
    private int size;
    private double loadFactor;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, double loadFactor) {
        this.buckets = new Collection[initialCapacity];
        this.loadFactor = loadFactor;
        this.size = 0;
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    private int getIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int bucketIndex = 0;
            private Iterator<Node> bucketIterator = buckets[0].iterator();

            @Override
            public boolean hasNext() {
                while (bucketIndex < buckets.length && !bucketIterator.hasNext()) {
                    bucketIndex++;
                    if (bucketIndex < buckets.length) {
                        bucketIterator = buckets[bucketIndex].iterator();
                    }
                }
                return bucketIndex < buckets.length && bucketIterator.hasNext();
            }

            @Override
            public K next() {
                return bucketIterator.next().key;
            }
        };
    }


    @Override
    public void forEach(Consumer<? super K> action) {
        Map61B.super.forEach(action);
    }

    @Override
    public Spliterator<K> spliterator() {
        return Map61B.super.spliterator();
    }


    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */


    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public V remove(K key) {
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];

        Iterator<Node> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.key.equals(key)) {
                V value = node.value;
                iterator.remove();
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    keys.add(node.key);
                }
            }
        }
        return keys;
    }

    @Override
    public void clear() {
        buckets = new Collection[buckets.length];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return false;
        }
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];

        // 检查桶中是否已存在该键，如果存在则更新
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        // 如果键不存在，则插入新节点
        bucket.add(new Node(key, value));
        size++;

        // 如果负载因子超过阈值，则扩容
        if (size > loadFactor * buckets.length) {
            resize();
        }
    }


    private void resize() {
        int newCapacity = buckets.length * 2;
        Collection<Node>[] newBuckets = new Collection[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = createBucket();
        }

        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                int newIndex = Math.floorMod(node.key.hashCode(), newCapacity);
                newBuckets[newIndex].add(node);
            }
        }
        buckets = newBuckets;
    }


}
