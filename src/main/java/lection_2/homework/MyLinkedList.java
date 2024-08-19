package lection_2.homework;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

/**
 * Implementation of the List interface as two ways linked list. Class
 * MyLinkedList stores links to the first and the last elements. Each
 * element ListElement stores the element and links to previous and next
 * elements of the list.
 *
 * @param <T> the type of elements in this list
 * @author Aleksei Durandin
 */
public class MyLinkedList<T> implements List<T> {

    int size;

    ListElement<T> head;

    ListElement<T> tail;

    /**
     * Returns size of this list.
     *
     * @return size of this list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return true if there are no elements in this list.
     *
     * @return true if there are no elements in this list.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns true if this list contains specified object
     *
     * @param o element whose presence in this list is to be tested
     * @return true if specified element is present in this list
     */
    @Override
    public boolean contains(Object o) {
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) return true;
        }
        return false;
    }

    /**
     * Returns an array with all elements of this list. Sequence of elements in
     * resulting array is the same as sequence in this list.
     *
     * @return An array with all elements of this list.
     */
    @Override
    public Object[] toArray() {
        Object[] resultArray = new Object[size];
        int i = 0;
        for (ListElement<T> e = head; e != null; e = e.next) {
            resultArray[i++] = e.element;
        }
        return resultArray;
    }

    /**
     * Returns an array with all elements of this list. Sequence of elements in
     * resulting array is the same as sequence in this list. If this list fits
     * the specified array, elements will be copied to it. Otherwise, will be
     * created new array with size of this list.
     *
     * @param a the array into which the elements of this list are to be stored,
     *          if it is big enough; otherwise, a new array of the same runtime
     *          type is allocated for this purpose.
     * @return an array with elements of this list
     * @throws NullPointerException - if the specified array is null.
     * @throws ArrayStoreException  - if an element this list could not be
     *                              stored into the dest array because of a type mismatch.
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (!a.getClass().getComponentType().isAssignableFrom(head.element.getClass())) throw new ArrayStoreException();
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

    /**
     * Appends this list with specified element. If list is already full, increases
     * it`s capacity.
     *
     * @param t element whose presence in this collection is to be ensured
     * @return true if element was successfully added
     */
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

    /**
     * Removes the specified element from this list, if present.
     *
     * @param o element to be removed from this list, if present
     * @return true if element was successfully removed.
     */
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

    /**
     * Returns true if all the elements of specified collection
     * are presented in this list.
     *
     * @param c collection to be checked for containment in this list
     * @return true if all the elements of specified collection
     * are presented in this list.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        if (!c.getClass().getComponentType().isAssignableFrom(head.element.getClass())) throw new ClassCastException();
        for (Object e : c) {
            if (!contains(e)) return false;
        }
        return true;
    }

    /**
     * Appends this list with all the elements of the specified collection.
     * The sequence of elements in this list will be the same as the
     * sequence of elements in collection.
     *
     * @param c collection containing elements to be added to this collection
     * @return true if all elements of the specified collection where added.
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (Object e : c)
            if (!add((T) e)) return false;
        return true;
    }

    /**
     * Inserts all the elements of the specified collection into this list
     * starting from specified index. The sequence of elements in this list
     * will be the same as the sequence of elements in collection.
     * All elements of this list from the specified index to end of the list
     * will be overwritten with new indexes ([new index] = [old index] + c.size)
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c     collection containing elements to be added to this list
     * @return true if all elements of the specified collection where added.
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkPositionIndex(index);
        for (Object e : c) {
            insertValue(element(index), (T) e);
            index++;
        }
        return true;
    }

    private void insertValue(ListElement<T> listElement, T element) {
        ListElement<T> link = listElement.next;
        listElement.next = new ListElement<>(element, null, listElement);
        listElement = listElement.next;
        listElement.next = link;
        if (link != null) link.previous = listElement;
        else tail = listElement;
        size++;
    }

    /**
     * Removes all elements from specified collection from this list if present
     *
     * @param c collection containing elements to be removed from this list
     * @return true if at least one element was removed.
     * @throws ClassCastException   if the class of an element of this list
     *                              is incompatible with the specified collection
     * @throws NullPointerException if this list contains a null element and the
     *                              specified collection does not permit null elements
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isChanged = false;
        for (Object e : c) {
            isChanged |= remove(e);
        }
        return isChanged;
    }

    /**
     * Removes from this list all of its elements that are not contained in
     * the specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return true if at least one element was removed.
     * @throws ClassCastException   if the class of an element of this list
     *                              is incompatible with the specified collection
     * @throws NullPointerException if this list contains a null element and the
     *                              specified collection does not permit null elements
     */
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

    /**
     * Removes all elements of this list.
     */
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

    /**
     * Returns element of this list with the specified index.
     *
     * @param index index of the element to return
     * @return element of this list with the specified index.
     * @throws IndexOutOfBoundsException - if index is out of range 0 >= index > size
     */
    @Override
    public T get(int index) {
        checkElementIndex(index);
        return element(index).element;
    }

    /**
     * Sets specified element to this list on the specified position.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return old value of element on specified position
     * @throws IndexOutOfBoundsException - if index is out of range 0 >= index > size
     */
    @Override
    public T set(int index, T element) {
        checkElementIndex(index);
        T oldValue = element(index).element;
        element(index).element = element;
        return oldValue;
    }

    /**
     * Inserts the specified element to the list on specified position.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException - if index is out of range 0 >= index > size
     */
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

    /**
     * Removes the element of this list with the specified index. All elements with
     * indexes in range from index + 1 to last index in this list are moving "left"
     *
     * @param index the index of the element to be removed
     * @return the value of deleted element
     * @throws IndexOutOfBoundsException - if index is out of range 0 >= index > size
     */
    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return removeElement(element(index));
    }

    /**
     * Returns index of first occurrence of specified element in this list. If element
     * was not found returns -1
     *
     * @param o element to search for
     * @return index of first found element or -1
     */
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

    /**
     * Returns index of last occurrence of specified element in this list. If element
     * was not found returns -1
     *
     * @param o element to search for
     * @return index of last found element or -1
     */
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

    /**
     * Returns an implementation of Iterator<T> interface for this list. The cursor
     * will point on the first element of the list
     *
     * @return MyIterator
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {

        ListElement<T> cursor;

        ListElement<T> lastReturned;

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

    /**
     * Returns an implementation of ListIterator<T> interface for this list. The cursor
     * will point on the first element of the list
     *
     * @return MyListIterator
     */
    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator(0);
    }

    /**
     * Returns an implementation of ListIterator<T> interface for this list. The cursor
     * will point on the element with the specified index.
     *
     * @param index index of the first element to be returned from the
     *              list iterator (by a call to {@link ListIterator#next next})
     * @return MyListIterator
     * @throws IndexOutOfBoundsException - if index is out of range 0 >= index > size
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        checkPositionIndex(index);
        return new MyListIterator(index);
    }

    private class MyListIterator extends MyIterator implements ListIterator<T> {

        int cursorIndex;

        MyListIterator() {
            new MyListIterator(0);
        }

        MyListIterator(int index) {
            cursor = element(index);
            cursorIndex = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor.previous != null;
        }

        @Override
        public T previous() {
            if (cursor.previous == null) {
                throw new NoSuchElementException();
            }
            lastReturned = cursor;
            cursor = cursor.previous;
            cursorIndex--;
            return (T) lastReturned.element;
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
            removeElement(lastReturned);
        }

        @Override
        public void forEachRemaining(Consumer action) {
            super.forEachRemaining(action);
        }

        @Override
        public void set(T t) {
            if (lastReturned == null)
                throw new IllegalStateException();
            lastReturned.element = t;
        }

        @Override
        public void add(T t) {
            insertValue(lastReturned, t);
        }
    }

    /**
     * Returns the subList which is the view of part of this list from {@code fromIndex},
     * inclusive, to {@code toIndex}, exclusive. Elements of the returned list are linked
     * to elements of original list. So all changes of returned list will reflect in the
     * original list.
     *
     * Returned list supports methods size(), set(int index), get(int index),
     * add(int index, T element), remove(int index).
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return subList from fromIndex to toIndex
     * @throws IndexOutOfBoundsException - if index is out of range 0 >= index > size
     * @throws IllegalArgumentException - if toIndex < fromIndex
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex == toIndex) return null;
        checkElementIndex(fromIndex);
        checkElementIndex(toIndex);
        if (fromIndex > toIndex) throw new IllegalArgumentException("fromIndex(" + fromIndex +
                ") is grater than toIndex( +" + toIndex + ")!");
        return new MySubList(this, fromIndex, toIndex);
    }

    private static class MySubList<T> extends AbstractList<T> {

        private MyLinkedList<T> list;

        private int fromIndex;

        private int size;

        MySubList(MyLinkedList<T> list, int fromIndex, int toIndex) {
            this.list = list;
            this.fromIndex = fromIndex;
            this.size = toIndex - fromIndex;
        }

        private void checkElementIndex(int index) {
            if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        }

        private void checkPositionIndex(int index) {
            if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        }

        public T set(int index, T element) {
            checkElementIndex(index);
            return list.set(fromIndex + index, element);
        }

        @Override
        public T get(int index) {
            checkElementIndex(index);
            return list.get(fromIndex + index);
        }

        @Override
        public int size() {
            return size;
        }

        public void add(int index, T element) {
            checkPositionIndex(index);
            list.add(fromIndex + index, element);
            size++;
        }

        public T remove(int index) {
            checkElementIndex(index);
            size--;
            return list.remove(fromIndex + index);
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
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
