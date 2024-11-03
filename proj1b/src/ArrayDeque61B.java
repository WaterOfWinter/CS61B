import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import static java.lang.System.arraycopy;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

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
        return null;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= items.length) {
            return null;
        }
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
