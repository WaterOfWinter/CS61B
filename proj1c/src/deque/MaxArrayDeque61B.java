package deque;

import java.util.ArrayDeque;
import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> comparator;
    public MaxArrayDeque61B(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    public T max() {
        return max(this.comparator);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }

        T maxElement = this.get(0); // 假设有 get(i) 方法可以获取元素
        for (int i = 1; i < this.size(); i++) {
            T currentElement = this.get(i);
            if (c.compare(currentElement, maxElement) > 0) {
                maxElement = currentElement;
            }
        }
        return maxElement;
    }
}

