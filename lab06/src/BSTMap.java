import org.checkerframework.checker.units.qual.K;
import org.w3c.dom.Node;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    public class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }
    @Override
    public void put(K key, V value) {
         root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        }
        else if (cmp > 0) {
            node.right = put(node.right, key, value);
        }
        else {
            node.value = value;
        }
        return node;
    }

    @Override
    public V get(K key) {
        return  get(root, key);
    }
    private V get(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        }
        else if (cmp > 0) {
            return get(node.right, key);
        }
        else {
            return node.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }
    private boolean containsKey(Node node, K key) {
        if (node == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return containsKey(node.left, key);
        }
        else if (cmp > 0) {
            return containsKey(node.right, key);
        }
        else {
            return true;
        }
    }

    @Override
    public int size() {
        return size(root);
    }
    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return size(node.left) + size(node.right) + 1;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new TreeSet<>(); // 使用 TreeSet 确保排序
        collectKeys(root, keys);
        return keys;
    }

    private void collectKeys(Node node, Set<K> keys) {
        if (node == null) {
            return;
        }
        collectKeys(node.left, keys);
        keys.add(node.key);
        collectKeys(node.right, keys);
    }

    @Override
    public V remove(K key) {
        V value = get(key); // 获取值以便在删除时返回
        root = remove(root, key);
        return value;
    }
    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            // 找到要删除的节点
            if (node.left == null) {
                return node.right; // 无左子树，直接返回右子树
            } else if (node.right == null) {
                return node.left; // 无右子树，直接返回左子树
            }
            // 有两个子树，找到右子树的最小节点
            node.key = min(node.right).key;
            node.value = min(node.right).value;
            node.right = remove(node.right, node.key); // 删除右子树的最小节点
        }
        return node;
    }

    private Node min(Node node) {
        if (node == null) {
            return null; // 如果节点为null，返回null
        }
        while (node.left != null) {
            node = node.left;
        }
        return node; // 返回找到的最小节点
    }


    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }


    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }
    private void printInOrder(Node node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.print(node.key + " ");
        printInOrder(node.right);
    }
}
