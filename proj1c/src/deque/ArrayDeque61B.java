package deque;

import deque.Deque61B;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;
import java.util.NoSuchElementException;

import static java.lang.System.arraycopy;

public class ArrayDeque61B<T> implements Deque61B<T> {

    protected int size;
    protected T[] items;
    protected int nextFirst;
    protected int nextLast;

    public ArrayDeque61B() {
        size = 0;
        items = (T[])new Object[8];
        nextFirst = 2;
        nextLast = 3;

    }

    private void shrink() {
        if (items.length > 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
    }


    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        int firstIndex = Math.floorMod(nextFirst + 1, items.length);
        if (firstIndex + size <= items.length) {
            arraycopy(items, firstIndex, temp, 1, size);
        } else {
            int part1Length = items.length - firstIndex;
            arraycopy(items, firstIndex, temp, 1, part1Length);
            arraycopy(items, 0, temp, part1Length + 1, size - part1Length);
        }
        items = temp;
        nextFirst = 0;
        nextLast = size + 1;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;

    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, items.length);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (size == 0) {
            return returnList;
        }
        int temp = Math.floorMod(nextFirst + 1, items.length);
        for (int i = 0; i < size; i++) {
            returnList.add(items[Math.floorMod(temp + i, items.length)]);
        }
        return returnList;
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
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        T item = items[nextFirst];
        items[nextFirst] = null;
        size--;
        shrink();
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = Math.floorMod(nextLast - 1, items.length);
        T item = items[nextLast];
        items[nextLast] = null;
        size--;
        shrink();
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= items.length) {
            return null;
        }
        int actualIndex = Math.floorMod(nextFirst + 1 + index, items.length);
        return items[actualIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }


    private class ArrayDequeIterator implements Iterator<T> {
        private int currentIndex;
        private int count;

        public ArrayDequeIterator() {
            currentIndex = Math.floorMod(nextFirst + 1, items.length);
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the deque.");
            }
            T item = items[currentIndex];
            currentIndex = Math.floorMod(currentIndex + 1, items.length);
            count++;
            return item;
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof ArrayDeque61B other) {
            if (size == other.size) {
                for (int i = 0; i < size; i++) {
                    if (!items[Math.floorMod(i+1+nextFirst, items.length)].equals(other.items[Math.floorMod(i+1+other.nextFirst, other.items.length)])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(get(i)).append(" ");
        }
        return sb.toString();
    }
}
