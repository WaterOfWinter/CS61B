import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

     @Test
    public void addFirstTest() {
         // add_first_from_empty
         ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
         deque.addFirst(5);
         deque.addFirst(10);
         deque.addFirst(15);
         List<Integer> result1 = deque.toList();
         assertThat(result1.get(2)).isEqualTo(5);
         // add_first_nonempty
         deque.addFirst(18);
         List<Integer> result2 = deque.toList();
         assertThat(result2.get(2)).isEqualTo(10);

         // add_first_trigger_resize
         deque.addFirst(12);
         deque.addFirst(13);
         deque.addFirst(14);
         deque.addFirst(15);
         deque.addFirst(16);
         deque.addFirst(17);
         deque.addFirst(18);
         deque.addFirst(19);
         deque.addFirst(20);
         deque.addFirst(21);
         List<Integer> result3 = deque.toList();
         assertThat(result3.get(2)).isEqualTo(19);

         ArrayDeque61B<Integer> deque2 = new ArrayDeque61B<>();
         deque2.addFirst(5);
         deque2.addFirst(10);
         deque2.addFirst(15);
         deque2.removeFirst();
         List<Integer> result4 = deque2.toList();
         assertThat(result4.get(0)).isEqualTo(10);

         // add_first_after_remove_to_empty
         deque2.removeFirst();
         deque2.removeFirst();
         List<Integer> result5 = deque2.toList();
         assertThat(result5).isEqualTo(new ArrayList<>());

         deque2.addFirst(12);
         assertThat(deque2.toList()).containsExactly(12); // yyyyyyyyyyyyyyyyyyyyyyyyyyyyy
     }

     @Test
    public void addLastTest() {
         // add_last_from_empty
         ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
         deque.addLast(5);
         deque.addLast(10);
         deque.addLast(15);
         List<Integer> result1 = deque.toList();
         assertThat(result1.get(2)).isEqualTo(15);

         // add_last_nonempty
         deque.addLast(18);
         List<Integer> result2 = deque.toList();
         assertThat(result2.get(1)).isEqualTo(10);

         // add_last_trigger_resize
         deque.addLast(19);
         deque.addLast(12);
         deque.addLast(17);
         deque.addLast(18);
         deque.addLast(10);
         deque.addLast(121);
         deque.addLast(178);
         List<Integer> result3 = deque.toList();
         assertThat(result3.get(2)).isEqualTo(15);

         ArrayDeque61B<Integer> deque2 = new ArrayDeque61B<>();
         deque2.addLast(3);
         deque2.addLast(15);
         deque2.addLast(10);
         deque2.removeLast();
         List<Integer> result4 = deque2.toList();
         assertThat(result4.get(1)).isEqualTo(15);

         // add_last_after_remove_to_empty
         deque2.removeLast();
         deque2.removeLast();
         List<Integer> result5 = deque2.toList();
         assertThat(result5).isEqualTo(new ArrayList<>());

         deque2.addLast(12);
         assertThat(deque2.toList()).containsExactly(12);
     }

     @Test
    public void getTest() {
          ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
          deque.addFirst(5);
          deque.addFirst(10);
          deque.addFirst(15);
          deque.addFirst(7);
          // get_valid
          assertThat(deque.toList().get(2)).isEqualTo(10);
          // get_oob_large: Check that get works on a large, out of bounds index.
          assertThat(deque.get(100)).isEqualTo(null);
          // get_oob_neg: Check that get works on a negative index.
          assertThat(deque.get(-100)).isEqualTo(null);

     }

     @Test
    public void isEmptyTest() {
          // is_empty_true
         ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
         assertThat(deque.isEmpty()).isTrue();

         // is_empty_false
         deque.addFirst(5);
         deque.addFirst(10);
         deque.addFirst(15);
         assertThat(deque.isEmpty()).isFalse();
     }

     @Test
    public void sizeTest() {
          // size: Check that size works.
         ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
         int result1 = deque.size();
         assertThat(result1).isEqualTo(0);

         deque.addFirst(5);
         deque.addFirst(10);
         deque.addFirst(15);
         int result2 = deque.size();
         assertThat(result2).isEqualTo(3);

         //size_after_remove_to_empty
          deque.removeFirst();
          deque.removeFirst();
          deque.removeFirst();
          int result3 = deque.size();
          assertThat(result3).isEqualTo(0);

          deque.removeFirst();
          deque.removeFirst();
          int result4 = deque.size();
          assertThat(result4).isEqualTo(0);

     }

     @Test
    public void removeFirstTest() {
         // remove_first
         ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
         deque.addFirst(5);
         deque.addFirst(10);
         deque.addFirst(15);
         deque.removeFirst();
         List<Integer> result1 = deque.toList();
         assertThat(result1.get(0)).isEqualTo(10);

         // remove_first_to_empty
         deque.removeFirst();
         deque.removeFirst();
         List<Integer> result2 = deque.toList();
         assertThat(result2).isEqualTo(new ArrayList<>());
     }

     @Test
    public void removeLastTest() {
         // remove_last
         ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
         deque.addLast(3);
         deque.addLast(15);
         deque.addLast(10);
         deque.removeLast();
         List<Integer> result1 = deque.toList();
         assertThat(result1.get(1)).isEqualTo(15);

         // remove_last_to_empty
         deque.removeLast();
         deque.removeLast();
         List<Integer> result2 = deque.toList();
         assertThat(result2).isEqualTo(new ArrayList<>());
     }

     @Test
    public void getRecursiveTest() {
         ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
         deque.addFirst(5);
         deque.addFirst(10);
         deque.addFirst(15);
         deque.addFirst(7);
         deque.addLast(15);
         List<Integer> result1 = deque.toList();
         assertThat(result1.get(2)).isEqualTo(10);
     }


     @Test
     public void toListTest() {
          // to_list_empty
          ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
          assertThat(deque.toList()).isEmpty();

          // to_list_nonempty
          deque.addFirst(5);
          deque.addFirst(10);
          deque.addFirst(15);
          deque.addFirst(7);
          assertThat(deque.toList()).containsExactly(5, 10, 15, 7);
     }
}



