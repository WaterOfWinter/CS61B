import deque.Deque61B;
import deque.LinkedListDeque61B;

public class IteratorTest {
    public static void main(String[] args) {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        for (String s : lld1) {
            System.out.println(s);
        }
    }
}
