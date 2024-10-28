import deque.ArrayDeque61B;
import deque.MaxArrayDeque61B;
import org.junit.jupiter.api.*;

import java.util.Comparator;
//import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.assertEquals;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }
    private static class StringAlphabeticComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }


    @Test
    public void testMaxWithCustomObjectComparator() {
        MaxArrayDeque61B<String> deque = new MaxArrayDeque61B<>(new StringLengthComparator());
        deque.addFirst("apple");
        deque.addFirst("pear");
        deque.addLast("banana");
        assertThat(deque.max(new StringAlphabeticComparator())).isEqualTo("pear");  // 按字符串长度比较，"banana" 是最长的
    }
}


