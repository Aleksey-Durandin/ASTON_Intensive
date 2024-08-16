package lection_2.homework;

public class DataType implements Comparable<DataType>{

    private int data;

    DataType(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "<" + data + ">";
    }

    @Override
    public int compareTo(DataType o) {
        return this.data - o.data;
    }
}
