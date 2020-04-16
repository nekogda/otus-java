package hw02.array;

import java.util.*;

public class DIYarrayList<T> implements List<T> {

    private static final int DEFAULT_CAP = 10;
    private int size = 0;
    private T[] data;

    public DIYarrayList() {
        this(DEFAULT_CAP);
    }

    public DIYarrayList(int cap) {
        if (cap < 0) {
            throw new IllegalArgumentException("should be >= 0, got " + cap);
        }
        data = (T[]) new Object[cap];
    }

    private void resize() {
        data = Arrays.copyOf(data, calcNewSize());
    }

    private void checkBounds(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    public T get(int index) {
        checkBounds(index);
        return data[index];
    }

    @Override
    public T set(int index, T element) {
        checkBounds(index);
        T oldValue = data[index];
        data[index] = element;
        return oldValue;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]");
        for (T item : Arrays.asList(data).subList(0, size())) {
            sj.add(item.toString());
        }
        return sj.toString();
    }

    private int calcNewSize() {
        int newSize = data.length << 1;
        if (newSize < 0) {
            throw new OutOfMemoryError();
        }
        return newSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DIYarrayList<?> that = (DIYarrayList<?>) o;
        return size == that.size &&
                Arrays.equals(data, 0, size, that.data, 0, that.size);
    }

    @Override
    public int hashCode() {
        if (data == null) {
            return 0;
        }

        int result = 1;

        for (T element : Arrays.asList(data).subList(0, size))
            result = 31 * result + (element == null ? 0 : element.hashCode());

        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean add(T e) {
        if (size >= data.length) {
            resize();
        }
        data[size] = e;
        size++;
        return true;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DIYarrayListIterator();
    }

    class DIYarrayListIterator implements ListIterator<T> {
        private int currentPosition = -1;

        @Override
        public boolean hasNext() {
            return currentPosition + 1 < DIYarrayList.this.size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentPosition++;
            return DIYarrayList.this.get(currentPosition);
        }

        @Override
        public void set(T t) {
            if (currentPosition == -1) {
                throw new IllegalStateException();
            }
            DIYarrayList.this.set(currentPosition, t);
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();

    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}

