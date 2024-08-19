package lection_2.homework;

import lection_2.homework.MergeSort.MergeSort;

import java.util.LinkedList;
import java.util.List;

public class Homework {

    public static void main(String[] args) {
        List<Integer> list = List.of(5, 3, 6, 1);
        System.out.println(list);
        System.out.println(MergeSort.sort(list));

        List<DataType> list2 = List.of(new DataType(5), new DataType(8), new DataType(26), new DataType(2));
        System.out.println(list2);
        System.out.println(MergeSort.sort(list2));

        LinkedList list3 = new LinkedList<Integer>();
        list3.add(5);
        list3.add(8);
        list3.add(3);
        Object[] arr = new Object[]{1, 1, 1, 1, 1, 1};

        System.out.println(list3);
        System.out.println("------");

        Object[] arr2 = list3.toArray(arr);
        System.out.println("arr");
        for (Object e: arr) {
            System.out.println(e);
        }
        System.out.println("arr2");
        for (Object e: arr2) {
            System.out.println(e);
        }
    }
}
