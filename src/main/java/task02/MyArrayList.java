package task02;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

/**
 * @param <T> This ArrayList implementation does not support adding null element,
 *            because of inability to change or delete added element.
 *            And the second reason is that null element can't be used in
 *            number of cases with forEach loop.
 *            Also it does not support any kind of element removing or replacement.
 */
public class MyArrayList<T>
        extends AbstractList<T> implements List<T>, RandomAccess, Serializable, Cloneable {

    private int initCapacity = 10;
    @SuppressWarnings("unchecked")
    private T[] elements = (T[]) new Object[initCapacity];
    private int size = 0;

    public MyArrayList() {
    }

    public MyArrayList(int capacity) {
        if (capacity < 0) {
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
        if (required == null || this.size == 0) {
            return false;
        }
        return this.stream().anyMatch(el -> el.equals(required));
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public T[] toArray() {
        return (T[]) Arrays.copyOf(elements, this.size);
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

    @SuppressWarnings("unchecked")
    public boolean add(final T element) {
        validateOnNotNull(element);
        if (elements.length == 0) {
            elements = (T[]) new Object[1];
        } else if (elements.length == size) {
            increaseCapacity();
        }
        elements[size++] = element;
        return true;
    }

    @Override
    public void add(final int insertionIndex, final T element) {
        validateIndexConsideringNotEqualsToSize(insertionIndex);
        validateOnNotNull(element);

        System.arraycopy(elements, insertionIndex, elements, insertionIndex + 1, size - insertionIndex);
        elements[insertionIndex] = element;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(final Collection<? extends T> src) {
        Objects.requireNonNull(src);

        if (src.isEmpty()) {
            return false;
        }
        int newCapacity = src.size() + this.size;
        if (elements.length < newCapacity) {
            increaseCapacity(newCapacity);
        }
        src.stream()
                .filter(Objects::nonNull)
                .forEach(this::add);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(final int insertionIndex, final Collection<? extends T> src) {
        validateIndexConsideringNotEqualsToSize(insertionIndex);
        Objects.requireNonNull(src);
        if (src.isEmpty()) {
            return false;
        }

        T[] extraElements = (T[]) src.stream()
                .filter(Objects::nonNull)
                .toArray();
        int srcLength = extraElements.length;
        elements = (T[]) new Object[this.size + srcLength];

        System.arraycopy(elements, insertionIndex, elements, insertionIndex + 1, size - insertionIndex);
        System.arraycopy(extraElements, 0, elements, insertionIndex, srcLength);
        size += srcLength;
        return true;
    }

    @Override
    public boolean containsAll(final Collection<?> src) {
        Objects.requireNonNull(src);
        return src.stream().allMatch(this::contains);
    }

    @Override
    public List<T> subList(final int start, final int end) {
        if (end < start) {
            throw new IllegalArgumentException();
        }
        validateIndex(start);
        validateIndexConsideringNotEqualsToSize(end);
        if (start == end) {
            return new MyArrayList<>();
        }

        T[] extracted = Arrays.copyOfRange(elements, start, end);
        List<T> res = new MyArrayList<>();
        Arrays.stream(extracted)
                .forEachOrdered(res::add);
        return res;
    }

    @Override
    public int lastIndexOf(final Object required) {
        if (required == null) {
            return -1;
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
        if (required == null) {
            return -1;
        }
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
        Arrays.stream(Arrays.copyOfRange(elements, 0, size))
                .forEach(action);
    }

    public void trimToSize() {
        elements = Arrays.copyOfRange(elements, 0, size);
    }

    @Override
    protected Object clone() {
        T[] copy = Arrays.copyOfRange(elements, 0, size);
        return new MyArrayList<T>(Arrays.asList(copy));
    }

    @Override
    public void sort(Comparator<? super T> comparator) {
        Arrays.sort(elements, 0, size, comparator);
    }

    private class MyIterator implements Iterator<T> {
        int currentIndex;

        MyIterator() {
        }

        public boolean hasNext() {
            return currentIndex != MyArrayList.this.size;
        }

        public T next() {
            if (currentIndex >= MyArrayList.this.size) {
                throw new NoSuchElementException();
            }
            return (T) elements[currentIndex++];
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            while (hasNext()) {
                action.accept(next());
            }
        }
    }

    private void increaseCapacity() {
        elements = Arrays.copyOf(elements, size + (size / 2) + 1);
    }

    private void increaseCapacity(int newCapacity) {
        elements = Arrays.copyOf(elements, newCapacity);
    }

    private void validateIndex(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void validateIndexConsideringNotEqualsToSize(int index) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void validateOnNotNull(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
    }

/* This realization of ArrayList contains some Unsupported operations.
Basically it does not provide any kind of remove action.
Throws the UnsupportedOperationException.
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
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        throw new UnsupportedOperationException();
    }
}
