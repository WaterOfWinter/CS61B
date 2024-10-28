package gh2;

import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;

//Note: This file will not compile until you complete the Deque61B implementations
public class GuitarString {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque61B<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) (Math.round(SR / frequency));
        buffer = new ArrayDeque61B<>();
        for (int i = 0; i < capacity; i++) {
           buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // 使用了增强 for 循环，这实际上创建了 i 的副本，并不会改变 buffer 中的元素。因此，直接赋值给 i 不会对 buffer 内的元素产生任何影响。
        // 您可以通过先清空 buffer，然后重新用随机值填充的方法来完成 pluck()。可以用 removeFirst() 清空 buffer，并用 addLast() 插入新的随机值。
        int size = buffer.size();
        while (!buffer.isEmpty()) {
            buffer.removeLast();
        }
        for (int i = 0; i < size; i++) {
            double r = Math.random() - 0.5;
            buffer.addLast(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        int size = buffer.size();
        double temp = (buffer.toList().get(0) + buffer.toList().get(1)) / 2.0 * DECAY;
        buffer.removeFirst();
        buffer.addLast(temp);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.toList().get(0);
    }
}

