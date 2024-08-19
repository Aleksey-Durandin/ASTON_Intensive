package lection_2.homework;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the List interface based on an array.
 *
 * @param <T> the type of elements in this list
 * @author Aleksei Durandin
 */
public class MyArrayList<T> implements List<T> {

    /**
     * The array in which elements are stored.
     */
    private Object[] elements;

    /**
     * Count of stored elements
     */
    private int size;

    /**
     * Constructs an empty list with capacity [10]
     */
    MyArrayList() {
        this.elements = new Object[10];
    }

    /**
     * Constructs an empty list with specified capacity.
     *
     * @param capacity - initial capacity.
     * @throws IllegalArgumentException - if initial capacity is negative
     */
    public MyArrayList(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Negative initial capacity!");
        this.elements = new Object[capacity];
    }

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
        for (Object element : elements) {
            if (element.equals(o)) return true;
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
        return Arrays.copyOf(elements, elements.length);
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
     * @throws ArrayStoreException  - if an element in the src array could not be
     * stored into the dest array because of a type mismatch.
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < elements.length) {
            return (T1[]) toArray();
        }
        System.arraycopy(elements, 0, a, 0, elements.length);
        if (a.length > elements.length) {
            a[elements.length] = null;
        }
        return a;
    }

    private void increaseCapacity() {
        increaseCapacity((int) (size * 1.5) + 1);
    }

    private void increaseCapacity(int incrementSize) {
        elements = Arrays.copyOf(elements, elements.length + incrementSize);
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
        if (size == elements.length) {
            increaseCapacity();
        }
        elements[size] = t;
        size++;
        return true;
    }

    /**
     * Removes the specified element from this list, if present.
     *
     * @param o element to be removed from this list, if present
     * @return true if element was successfully removed.
     */
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                System.arraycopy(elements, i + 1, elements, i, elements.length - i);
                elements[size] = null;
                size--;
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
        for (Object o : c) {
            if (!contains(o)) return false;
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
        if (c.size() == 0) return false;
        if (size + c.size() > elements.length) {
            increaseCapacity(c.size());
        }
        for (Object o : c) {
            if (!add((T) o)) return false;
        }
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
        if (c.size() == 0) return false;
        if (size + c.size() > elements.length) {
            increaseCapacity(c.size());
        }
        for (Object o : c) {
            add(index++, (T) o);
        }
        return true;
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
        if (c.isEmpty()) return false;
        for (int i = 0; i < size; i++) {
            if (c.contains(elements[i])) elements[i] = null;
        }
        elements = Arrays.stream(elements).filter(e -> e != null).collect(Collectors.toList()).toArray();
        boolean isChanged = elements.length < size;
        size = elements.length;
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
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) elements[i] = null;
        }
        elements = Arrays.stream(elements).filter(e -> e != null).collect(Collectors.toList()).toArray();
        boolean isChanged = elements.length < size;
        size = elements.length;
        return isChanged;
    }

    /**
     * Removes all elements of this list.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
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
        return (T) elements[index];
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
        T oldValue = get(index);
        elements[index] = element;
        return oldValue;
    }

    /**
     * Inserts the specified element to the list on specified position. Elements
     * with indexes from specified to last index in list are moving "right" in list.
     * If list is already full, increases capacity.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException - if index is out of range 0 >= index > size
     */
    @Override
    public void add(int index, T element) {
        checkPositionIndex(index);
        if (size == elements.length) {
            increaseCapacity();
        }
        System.arraycopy(elements, index + 1, elements, index, size - index);
        elements[index] = element;
        size++;
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
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
        return set(size + 1, null);
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
        for (int i = 0; i < size; i++) {
            if (get(i).equals(o)) return i;
        }
        return -1;
    }

    /**
     * Returns index of last occurrence of specified element in this list. If element
     * was not found returns -1
     * @param o element to search for
     * @return index of last found element or -1
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (get(i).equals(o)) return i;
        }
        return -1;
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
     *        list iterator (by a call to {@link ListIterator#next next})
     * @return MyListIterator
     * @throws IndexOutOfBoundsException - if index is out of range 0 >= index > size
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyListIterator(index);
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

        int cursor;

        int lastReturned = -1;

        MyIterator() {
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            lastReturned = cursor;
            return (T) elements[cursor++];
        }
    }

    private class MyListIterator extends MyIterator implements ListIterator<T> {

        MyListIterator() {
            cursor = 0;
        }

        MyListIterator(int index) {
            checkElementIndex(index);
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            lastReturned = --cursor;
            return (T) elements[cursor];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(Object o) {
            if (lastReturned < 0) throw new IllegalStateException();
            elements[lastReturned] = (T) o;
        }

        @Override
        public void add(Object o) {
            if (o.getClass() != elements[0].getClass()) throw new ClassCastException();
            MyArrayList.this.add(cursor, (T) o);
            cursor++;
            lastReturned = -1;
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
        checkElementIndex(fromIndex);
        checkElementIndex(toIndex);
        if (fromIndex > toIndex) throw new IllegalArgumentException("fromIndex(" + fromIndex +
                ") is grater than toIndex( +" + toIndex + ")!");
        return new MySubList(this, fromIndex, toIndex);
    }

    private static class MySubList<T> extends AbstractList<T> {

        private MyArrayList<T> list;

        private int fromIndex;

        private int toIndex;

        private int size;

        public MySubList(MyArrayList<T> list, int fromIndex, int toIndex) {
            this.list = list;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
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

    public String toString() {
        ListIterator iterator = new MyListIterator();
        StringBuilder sb = new StringBuilder().append(iterator.hasNext() ? "[" + iterator.next() : ("["));
        while (iterator().hasNext()) {
            sb.append(", " + iterator.next());
        }
        return sb.append("]").toString();
    }
}
