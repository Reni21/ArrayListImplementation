package arraylistimpl;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class MyIteratorTest {

    private List<String> initMyList() {
        List<String> elements = new MyArrayList<>();
        elements.add("test1");
        elements.add("test2");
        elements.add("test3");
        elements.add("test4");
        return elements;
    }

    @Test
    public void shouldReturnTrueIfListHasNextElement() {
        List<String> elements = initMyList();;
        Iterator<String> instance = elements.iterator();
        boolean res = instance.hasNext();
        assertTrue(res);
    }

    @Test
    public void shouldReturnCorrectResultAfterCalledNextMethod() {
        List<String> elements = initMyList();;
        Iterator<String> instance = elements.iterator();
        instance.hasNext();
        String res = instance.next();
        assertEquals("test1", res);
    }

    @Test
    public void shouldIterateThrowCurrentListInCorrectOrder() {
        List<String> elements = initMyList();;
        Iterator<String> instance = elements.iterator();
        StringBuilder builder = new StringBuilder();
        while (instance.hasNext()) {
            builder.append(instance.next());
        }
        String res = builder.toString();
        assertEquals("test1test2test3test4", res);
    }

    @Test
    public void shouldReturnFalseIfListDoesNotContainNextElement() {
        List<String> elements = new MyArrayList<>();
        Iterator<String> instance = elements.iterator();
        boolean res = instance.hasNext();
        assertFalse(res);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfLisDoesNotContainIt() {
        List<String> elements = new MyArrayList<>();;
        Iterator<String> instance = elements.iterator();
        instance.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedOperationExceptionIfCalledRemoveWhileIterate() {
        List<String> elements = initMyList();;
        Iterator<String> instance = elements.iterator();
        instance.hasNext();
        instance.remove();
    }

    @Test
    public void shouldApplyRequiredActionForIchElement() {
        List<String> elements = initMyList();;
        Iterator<String> instance = elements.iterator();
        instance.next();

        AtomicInteger counter = new AtomicInteger(0);
        instance.forEachRemaining(el -> {
            counter.incrementAndGet();

        });
        assertEquals(3, counter.get());
    }

}
