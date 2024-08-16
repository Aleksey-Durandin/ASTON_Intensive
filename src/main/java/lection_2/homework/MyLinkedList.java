package lection_2.homework;

import java.lang.reflect.Array;
import java.util.*;

public class MyLinkedList<T> implements List<T> {

    int size;

    ListElement<T> head;

    ListElement<T> tail;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    private class MyIterator<T> implements Iterator<T> {

        private ListElement<T> cursor;

        private ListElement<T> lastReturned;

        MyIterator() {
            cursor = (ListElement<T>) head;
        }

        @Override
        public boolean hasNext() {
            return cursor.next != null;
        }

        @Override
        public T next() {
            if (cursor.next == null) {
                throw new NoSuchElementException();
            }
            lastReturned = cursor;
            cursor = cursor.next;
            return lastReturned.element;
        }
    }

    @Override
    public Object[] toArray() {
//        ПЕРЕПИСАТЬ
        Object[] resultArray = new Object[size];
        int i = 0;
        for (ListElement<T> e = head; e != null; e = e.next) {
            resultArray[i++] = e.element;
        }
        return resultArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
//        if (!a.getClass().getComponentType().isAssignableFrom(head.element.getClass()))
//        ПЕРЕПИСАТЬ
        if (a.length < size) {
            a = (T1[]) Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        for (ListElement<T> e = head; e != null; e = e.next) {
            a[i++] = (T1) e.element;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        tail.next = new ListElement<>(t, null, tail);
        tail = tail.next;
        size++;
        return true;
    }

    private T removeElement(ListElement<T> listElement) {
        if (listElement.previous == null) {
            head = listElement.next;
            head.previous = null;
        } else if (listElement.next == null) {
            tail = listElement.previous;
            tail.next = null;
        } else {
            listElement.previous.next = listElement.next;
            listElement.next.previous = listElement.previous;
        }
        size--;
        return listElement.element;
    }

    @Override
    public boolean remove(Object o) {
        if (o != null) {
            for (ListElement<T> e = head; e != null; e = e.next) {
                if (e.element.equals(o)) removeElement(e);
                return true;
            }
        } else {
            for (ListElement<T> e = head; e != null; e = e.next) {
                if (e.element == null) removeElement(e);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (!c.getClass().getComponentType().isAssignableFrom(head.element.getClass())) throw new ClassCastException();
        for (Object e : c) {
            if (!contains(e)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (Object e : c)
            if (!add((T) e)) return false;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkPositionIndex(index);
        for (Object e : c) {
            insertValue(element(index), (T) e);
            index++;
        }
        return true;
    }

    private void insertValue(ListElement<T> listElement, T element){
        ListElement<T> link = listElement.next;
        listElement.next = new ListElement<>(element, null, listElement);
        listElement = listElement.next;
        listElement.next = link;
        if (link != null) link.previous = listElement;
        else tail = listElement;
        size++;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isChanged = false;
        for (Object e: c) {
            isChanged |= remove(e);
        }
        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isChanged = false;
        for (ListElement<T> e = head; e != null; e = e.next) {
            if (!c.contains(e)) {
                isChanged |= remove(e);
            }
        }
        return isChanged;
    }

    @Override
    public void clear() {
        for (ListElement<T> e = head; e != null; ) {
            ListElement<T> next = e.next;
            e.element = null;
            e.next = null;
            e.previous = null;
            e = next;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return element(index).element;
    }

    @Override
    public T set(int index, T element) {
        checkElementIndex(index);
        T oldValue = element(index).element;
        element(index).element = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        checkPositionIndex(index);
        insertValue(element(index), element);
    }

    ListElement<T> element(int index) {
        ListElement<T> element;
        if (index < size / 2) {
            element = head;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
        } else {
            element = tail;
            for (int i = size - 1; i > index; i--) {
                element = element.previous;
            }
        }
        return element;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return removeElement(element(index));
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o != null) {
            for (ListElement<T> e = head; e != null; e = e.next, index++) {
                if (e.element.equals(o)) return index;
            }
        } else {
            for (ListElement<T> e = head; e != null; e = e.next, index++) {
                if (e.element == null) return index;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        if (o != null) {
            for (ListElement<T> e = tail; e != null; e = e.next, index--) {
                if (e.element.equals(o)) return index;
            }
        } else {
            for (ListElement<T> e = tail; e != null; e = e.next, index--) {
                if (e.element == null) return index;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator<>(0);
    }

    private class MyListIterator<T> implements ListIterator<T> {

        private ListElement<T> cursor;

        private ListElement<T> lastReturned;

        int cursorIndex;

        MyListIterator(int index) {
            cursor = (ListElement<T>) element(index);
            cursorIndex = index;
        }

        @Override
        public boolean hasNext() {
            return cursor.next != null;
        }

        @Override
        public T next() {
            if (cursor.next == null) { throw new NoSuchElementException(); }
            lastReturned = cursor;
            cursor = cursor.next;
            cursorIndex++;
            return lastReturned.element;
        }

        @Override
        public boolean hasPrevious() { return cursor.previous != null; }

        @Override
        public T previous() {
            if (cursor.previous == null) { throw new NoSuchElementException(); }
            lastReturned = cursor;
            cursor = cursor.previous;
            cursorIndex--;
            return lastReturned.element;
        }

        @Override
        public int nextIndex() {
            return cursorIndex;
        }

        @Override
        public int previousIndex() {
            return cursorIndex - 1;
        }

        @Override
        public void remove() {
//            removeElement((ListElement<T>) cursor);
        }

        @Override
        public void set(T t) {
            if (lastReturned == null)
                throw new IllegalStateException();
            lastReturned.element = t;
        }

        @Override
        public void add(T t) {
//            insertValue(lastReturned, t);
        }
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        checkPositionIndex(index);
        return new MyListIterator<>(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    private boolean isLegalElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isLegalElementIndex(index)) throw new IndexOutOfBoundsException();
    }

    private boolean isLegalPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkPositionIndex(int index) {
        if (!isLegalPositionIndex(index)) throw new IndexOutOfBoundsException();
    }

    private static class ListElement<T> {

        T element;
        ListElement<T> next;
        ListElement<T> previous;

        ListElement(T element, ListElement<T> next, ListElement<T> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

    }


}
