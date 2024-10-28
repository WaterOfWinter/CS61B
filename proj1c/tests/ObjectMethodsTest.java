import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObjectMethodsTest {

    @Test
    public void testLinkedListDeque() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addLast("front");
        lld.addLast("middle");
        lld.addLast("back");

        assertEquals(3, lld.size());
        assertEquals("front", lld.removeFirst());
        assertEquals("middle", lld.removeFirst());
        assertEquals("back", lld.removeFirst());
        assertTrue(lld.isEmpty());
    }

    @Test
    public void testArrayDeque() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        assertEquals(3, ad.size());
        assertEquals("front", ad.removeFirst());
        assertEquals("middle", ad.removeFirst());
        assertEquals("back", ad.removeFirst());
        assertTrue(ad.isEmpty());
    }

    @Test
    public void testEquality() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        Deque61B<String> lld2 = new LinkedListDeque61B<>();
        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertTrue(lld1.equals(lld2));


        Deque61B<String> ad1 = new ArrayDeque61B<>();
        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");
        assertFalse(lld1.equals(ad1));
    }

    @Test
    public void testToString() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        assertEquals("front middle back ", ad.toString());

        Deque61B<String> ld2 = new LinkedListDeque61B<>();
        ld2.addLast("front");
        ld2.addLast("middle");
        ld2.addLast("back");
        assertEquals("front middle back ", ld2.toString());
    }
}



