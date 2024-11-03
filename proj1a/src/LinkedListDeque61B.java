import java.util.List;
import java.util.ArrayList; // import the ArrayList class

public class LinkedListDeque61B<T> implements Deque61B<T> {
    public Node sentinel;
    public Node tail;
    public int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        tail = sentinel;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public class Node {
        public Node prev;
        public T item;
        public Node next;
        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel, sentinel.next);
        if (sentinel.next != null) {
            sentinel.next.prev = newNode;
        }
        sentinel.next = newNode;
        //if (tail == sentinel) {
        //    tail = newNode; // 如果队列原来为空，更新tail指向新节点
        //}
        size++;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, tail, sentinel);
        if (tail != null) {
            tail.next = newNode;
        }
        if (tail != sentinel) {
            tail.next = newNode;
        }
        else {
            sentinel.next = newNode;
        }
        size++;
        tail = newNode;
        sentinel.prev = newNode;
    }

    @Override
    public List<T> toList() {
        Node current = sentinel.next;
        if (size == 0) {
            return List.of();
        }
        List<T> result = new ArrayList<>(size);

        while (current != null && current != sentinel) {
            result.add(current.item);
            current = current.next;
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        int count = 0;
        Node current = sentinel.next;
        while (current != null && current != sentinel) {
            current = current.next;
            count++;
        }
        return count;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;  // Return null if deque is empty
        }

        // Get the first node (sentinel.next)
        Node firstNode = sentinel.next;

        // Update sentinel to skip over the first node
        sentinel.next = firstNode.next;
        firstNode.next.prev = sentinel;  // Maintain the doubly-linked structure

        size--;

        return firstNode.item;  // Return the removed item
    }


    @Override
    public T removeLast() {
        if (size == 0) {
            return null;  // If deque is empty, return null
        }

        // Get the last node (sentinel.prev), which we want to remove
        Node lastNode = sentinel.prev;

        // If there is only one element, set sentinel's next and prev to point to itself
        if (size == 1) {
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
        } else {
            // Remove the last node by linking sentinel.prev to the second-to-last node
            sentinel.prev = lastNode.prev;
            lastNode.prev.next = sentinel;
        }

        size--;
        return lastNode.item;  // Return the item that was in the last node
    }


    @Override
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        Node ptr = sentinel.next;
        for (int i = 0; i < index; i++) {
            if (ptr != null) {
                if (i == index - 1) {
                    return ptr.item;
                }
                ptr = ptr.next;
            }
        }

        return null;
    }

    @Override
    public T getRecursive(int index) {
        if (size == 0 || index < 0 || index >= size) {
            return null;
        }

        return getRecursiveHelper(sentinel.next, index);

    }

    private T getRecursiveHelper(Node current, int index) {
        if (index == 0) {
            return current.item;
        }
        else {
            return getRecursiveHelper(current.next, index - 1);
        }
    }
}
