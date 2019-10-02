package task02;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class MyArrayList<T>
        extends AbstractList<T> implements List<T>, RandomAccess, Serializable, Cloneable
{

    private int initCapacity = 10;
    @SuppressWarnings("unchecked")
    private T[] elements = (T[]) new Object[initCapacity];
    private int size = 0;

    public MyArrayList() {
    }

    public MyArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.initCapacity = capacity;
    }

    public MyArrayList(Collection<? extends T> src) {
        addAll(src);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object required) {
        return Arrays.asList(Arrays.copyOf(elements, this.size))
                .contains(required);
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public T[] toArray() {
        return Arrays.copyOf(elements, this.size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(final T1[] newContainer) {
        if (newContainer.length < size) {
            return (T1[]) Arrays.copyOf(elements, this.size, newContainer.getClass());
        }
        System.arraycopy(elements, 0, newContainer, 0, this.size);
        if (newContainer.length > this.size) {
            newContainer[this.size] = null;
        }
        return newContainer;
    }

    public boolean add(final T element) {
        if (elements.length <= size) {
            increaseCapacity();
        }
        elements[size++] = element;
        return true;
    }

    private void increaseCapacity() {
        elements = Arrays.copyOf(elements, size + (size / 2) + 1);
    }

    private void increaseCapacity(int newCapacity) {
        elements = Arrays.copyOf(elements, newCapacity);
    }

    @Override
    public void add(final int index, final T element) {
        validateIndex(index);

        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(final Collection<? extends T> src) {
        Objects.requireNonNull(src);

        if (src.isEmpty()) {
            elements = (T[]) new Object[0];
            return false;
        }
        int newCapacity = src.size() + this.size;
        if (elements.length < newCapacity) {
            increaseCapacity(newCapacity);
        }
        src.forEach(this::add);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(final int insertionIndex, final Collection<? extends T> src) {
        validateIndex(insertionIndex);
        Objects.requireNonNull(src);

        T[] extraElements = (T[]) src.toArray();
        int srcLength = extraElements.length;
        T[] buffer = elements;
        elements = (T[]) new Object[this.size + srcLength];

        System.arraycopy(buffer, 0, elements, 0, insertionIndex);
        System.arraycopy(extraElements, 0, elements, insertionIndex, srcLength);
        System.arraycopy(buffer, insertionIndex, elements, srcLength + insertionIndex, size - insertionIndex);

        size = size + srcLength;
        return true;
    }

    private void validateIndex(int index) {
        if (index >= this.size || index < 0)
            throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean containsAll(final Collection<?> src) {
        Objects.requireNonNull(src);
        T[] copy = Arrays.copyOfRange(elements, 0, size);
        return Arrays.asList(copy).containsAll(src);
    }

    @Override
    public List<T> subList(final int start, final int end) {
        if (end < start) {
            throw new IllegalArgumentException();
        }
        validateIndex(start);
        validateIndex(end);
        if (start == end) {
            return new MyArrayList<>();
        }
        T[] res = Arrays.copyOfRange(elements, start, end);
        return Arrays.asList(res);
    }

    @Override
    public int lastIndexOf(final Object required) {
        if (required == null) {
            return IntStream.range(0, size)
                    .boxed()
                    .sorted(Collections.reverseOrder())
                    .filter(index -> elements[index] == null)
                    .findFirst()
                    .orElse(-1);
        }
        return IntStream.range(0, size)
                .boxed()
                .sorted(Collections.reverseOrder())
                .filter(index -> required.equals(elements[index]))
                .findFirst()
                .orElse(-1);
    }

    @Override
    public int indexOf(final Object required) {
        return IntStream.range(0, size)
                .filter(i -> required.equals(elements[i]))
                .findFirst()
                .orElse(-1);
    }

    @Override
    public T get(final int index) {
        validateIndex(index);
        return elements[index];
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        Arrays.stream(elements)
                .filter(Objects::nonNull)
                .forEach(action);
    }

    public void trimToSize() {
        elements = Arrays.copyOfRange(elements, 0, size);
    }

    public Object clone() {
        T[] newElements = Arrays.copyOfRange(elements, 0, size);
        return new MyArrayList<T>(Arrays.asList(newElements));
    }

    @Override
    public void sort(Comparator<? super T> comparator) {
        Arrays.sort(elements, 0, size, comparator);
    }

    private class MyIterator implements Iterator<T> {
        int startedSize = MyArrayList.this.size;
        int currentIndex;

        MyIterator() {
        }

        public boolean hasNext() {
            return currentIndex != MyArrayList.this.size;
        }

        public T next() {
            if (startedSize != MyArrayList.this.size) {
                throw new ConcurrentModificationException();
            }

            if (currentIndex >= MyArrayList.this.size) {
                throw new NoSuchElementException();
            }
            return (T) elements[++currentIndex];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        // I am not sure does this method work correctly in all possible cases
        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            MyArrayList.this.forEach(action);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elements, size));
    }

    /**
     * This realization of ArrayList contains some Unsupported operations.
     * Basically it does not provide any kind of remove action and ListIterator.
     * Throws the UnsupportedOperationException.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(final Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final Collection<?> src) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(final Collection<?> src) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(final int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(final int index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator(final int index) {
        throw new UnsupportedOperationException();
    }


}
