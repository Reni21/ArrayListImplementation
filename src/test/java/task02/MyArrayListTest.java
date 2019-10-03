package task02;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;

public class MyArrayListTest {
    private List<String> test;

    @Before
    public void setUp(){
        test = new MyArrayList<>();
    }

    private void initializeMyLisAsNotEmpty(){
        test = new MyArrayList<>();
        test.add("test1");
        test.add("test2");
        test.add("test3");
        test.add("test1");
        test.add("test4");
    }

    @Test
    public void shouldReturnZeroIfListIsEmpty(){
        test = new MyArrayList<>();
        int res = test.size();
        assertEquals(0,res);
    }

    @Test
    public void shouldReturnCurrentListSize(){
        initializeMyLisAsNotEmpty();
        int res = test.size();
        assertEquals(5,res);
    }

    @Test
    public void shouldReturnTrueIfListIsEmpty(){
        test = new MyArrayList<>();
        boolean res = test.isEmpty();
        assertTrue(res);
    }

    @Test
    public void shouldReturnTrueIfMyListContainRequiredElement(){
        initializeMyLisAsNotEmpty();
        boolean res = test.contains("test1");
        assertTrue(res);
    }

    @Test
    public void shouldReturnFalseIfMyListDoesNotContainRequiredElement(){
        initializeMyLisAsNotEmpty();
        boolean res = test.contains("search");
        assertFalse(res);
    }

    @Test
    public void shouldReturnFalseIfRequiredElementForSearchIsNull(){
        test = new MyArrayList<>();
        boolean res = test.contains(null);
        assertFalse(res);
    }

    @Test
    public void shouldReturnFalseIfMyListIsEmpty(){
        test = new MyArrayList<>();
        boolean res = test.contains("test");
        assertFalse(res);
    }
    @Test
    public void shouldReturnMyListIterator(){
        Iterator<String> res = test.iterator();
        assertNotNull(res);
    }

    @Test
    public void shouldReturnArray(){  // ask about hamcrest
        initializeMyLisAsNotEmpty();
        String[] res = (String[]) test.toArray();
        //assertEquals(res instanceof Array);
        assertNotNull(res);
    }


}