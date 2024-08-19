package lection_2.homework.MergeSort;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    public static <T extends Comparable> List<T> sort(List<T> list) {
        List tmpList = new ArrayList<>(list);
        sort(tmpList, 0, tmpList.size()-1);
        return tmpList;
    }

    private static <T extends Comparable> void sort(List<T> list, int beginIndex, int endIndex) {
        if (beginIndex < endIndex) {
            int midIndex = (endIndex + beginIndex) / 2;
            sort(list, beginIndex, midIndex);
            sort(list, midIndex+1, endIndex);
            merge(list, beginIndex, midIndex, endIndex);
        }
    }

    private static <T extends Comparable> void merge(List<T> list, int beginIndex, int midIndex, int endIndex) {
        ArrayList<T> tmpList = new ArrayList<>();

        for (int i = beginIndex, j = midIndex + 1, k = 0; k < endIndex +1 ; ) {
            if (i > midIndex) {
                tmpList.addAll(list.subList(j, endIndex + 1));
                k += list.subList(j, endIndex + 1).size();
                break;
            }
            if (j > endIndex) {
                tmpList.addAll(list.subList(i, midIndex + 1));
                k += list.subList(i, j).size();
                break;
            }

            if (list.get(i).compareTo(list.get(j)) < 0) {
                tmpList.add(list.get(i));
                i++;
            } else {
                tmpList.add(list.get(j));
                j++;
            }
            k++;
        }

        for (int i = beginIndex, k = 0; i < endIndex + 1 ; i++, k++) {
            list.set(i, tmpList.get(k));
        }
    }
}
