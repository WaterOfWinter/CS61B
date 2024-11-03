import com.google.common.truth.IterableSubject;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         lld1.removeFirst();
         lld1.removeFirst();
         lld1.removeFirst();
         assertThat(lld1.toList()).containsExactly().inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         assertThat(lld1.toList()).containsExactly("front").inOrder();
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

     //Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void isEmptyTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.isEmpty()).isFalse();
         lld1.addFirst(0);
         assertThat(lld1.isEmpty()).isFalse();
         lld1.addFirst(1);
         assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void sizeTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         assertThat(lld1.size()).isEqualTo(0);
         lld1.addFirst(0);
         assertThat(lld1.size()).isEqualTo(1);

    }

    @Test
    public void removeFirstTest() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();
         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         lld1.removeFirst();
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();
         lld1.removeFirst();
         lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly().inOrder();
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly().inOrder();


         Deque61B<String> lld2 = new LinkedListDeque61B<>();
         lld2.addLast("front");
         lld2.removeFirst();
         assertThat(lld2.toList()).containsExactly().inOrder();

         Deque61B<String> lld3 = new LinkedListDeque61B<>();
         lld3.removeFirst();
         assertThat(lld3.toList()).containsExactly().inOrder();

    }

    @Test
    public void removeLastTest() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();
         lld1.addLast("front");
         lld1.addLast("middle");
         lld1.addLast("back");
         lld1.removeLast();
         assertThat(lld1.toList()).containsExactly("front","middle").inOrder();
        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly().inOrder();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly().inOrder();

        Deque61B<String> lld2 = new LinkedListDeque61B<>();
         lld2.addLast("front");
         lld2.removeLast();
         assertThat(lld2.toList()).containsExactly().inOrder();

         Deque61B<String> lld3 = new LinkedListDeque61B<>();
         lld3.removeLast();
         assertThat(lld3.toList()).containsExactly().inOrder();
    }

    @Test
    public void getTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         lld1.addLast(0);
         lld1.addLast(1);
         lld1.addLast(2);
         int result = lld1.get(2);
         assertThat(result).isEqualTo(1);
    }

    @Test
    public void getRecursiveTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         lld1.addLast(0);
         lld1.addLast(1);
         lld1.addLast(2);
         lld1.addLast(3);
         lld1.addLast(2);
         assertThat(lld1.getRecursive(2)).isEqualTo(2);
    }
}