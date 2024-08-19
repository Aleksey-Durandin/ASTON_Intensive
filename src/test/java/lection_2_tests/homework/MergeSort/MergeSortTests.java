package lection_2_tests.homework.MergeSort;

import lection_2.homework.MergeSort.MergeSort;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTests {

    @Test
    void Test_1() {
        List<Integer> list = List.of(5, -3, 6, 1);
        assertEquals(MergeSort.sort(list), List.of(-3, 1, 5, 6));
    }

    @Test
    void Test_2() {
        List<Integer> list = List.of(1);
        assertEquals(MergeSort.sort(list), list);
    }

    @Test
    void Test_3() {
        List<Integer> list = List.of(1, 1, 1, 1);
        assertEquals(MergeSort.sort(list), list);
    }
}
