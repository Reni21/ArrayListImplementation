package arraylistimpl;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class MyArrayListTest {

    private List<String> initMyList() {
        List<String> instance = new MyArrayList<>();
        instance.add("test1");
        instance.add("test2");
        instance.add("test3");
        instance.add("test4");
        return instance;
    }

    // size() tests
    @Test
    public void shouldReturnZeroIfListIsEmpty() {
        List<String> instance = new MyArrayList<>();
        int res = instance.size();
        assertEquals(0, res);
    }

    @Test
    public void shouldReturnCurrentListSize() {
        List<String> instance = initMyList();
        int res = instance.size();
        assertEquals(4, res);
    }

    // isEmpty() tests

    @Test
    public void shouldReturnTrueIfListIsEmpty() {
        List<String> instance = new MyArrayList<>();
        boolean res = instance.isEmpty();
        assertTrue(res);
    }

    // contains(Element el) tests

    @Test
    public void shouldReturnTrueIfMyListContainRequiredElement() {
        List<String> instance = initMyList();
        boolean res = instance.contains("test1");
        assertTrue(res);
    }

    @Test
    public void shouldReturnFalseIfMyListDoesNotContainRequiredElement() {
        List<String> instance = initMyList();
        boolean res = instance.contains("search");
        assertFalse(res);
    }

    @Test
    public void shouldReturnFalseIfRequiredElementForSearchIsNull() {
        List<String> instance = new MyArrayList<>();
        boolean res = instance.contains(null);
        assertFalse(res);
    }

    @Test
    public void shouldReturnFalseIfMyListIsEmpty() {
        List<String> instance = new MyArrayList<>();
        boolean res = instance.contains("test");
        assertFalse(res);
    }

    // iterator() test

    @Test
    public void shouldReturnMyListIterator() {
        List<String> instance = initMyList();
        Iterator<String> res = instance.iterator();
        assertNotNull(res);
    }

    // toArray() tests

    @Test
    public void shouldReturnArrayOfObjectsIfListIsNotEmpty() {
        List<String> instance = initMyList();
        Object[] res = instance.toArray();
        assertThat(res).isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(4);
    }

    @Test
    public void shouldReturnEmptyArrayIfListIsEmpty() {
        List<String> instance = new MyArrayList<>();
        Object[] res = instance.toArray();
        assertThat(res).isNotNull().isEmpty();
    }

    @Test(expected = ClassCastException.class)
    public void shouldThrowClassCastExceptionWhenTryToCastResultToRequiredType() {
        List<String> instance = initMyList();
        String[] res = (String[]) instance.toArray();
    }

    // toArray(Array[] Src) test

    @Test
    public void shouldReturnNewArrayOfRequiredTypeIfRequiredArrayLengthLessThanListSize() {
        List<String> instance = initMyList();
        String[] test = {"new1", "new2"};
        String[] res = instance.toArray(test);
        assertNotSame(test, res);
    }

    @Test
    public void shouldFillGivenArrayWithElementsFromListIfArraysLengthIsBiggerOrEqualsOfListSize() {
        List<String> instance = initMyList();
        String[] test = {"new1", "new2", "new3", "new4", "new5"};
        String[] res = instance.toArray(test);
        assertSame(test, res);
    }

    @Test
    public void shouldKeepGivenArrayLengthIfItBiggerOrEqualsOfListSize() {
        List<String> instance = initMyList();
        String[] test = {"new1", "new2", "new3", "new4", "new5"};
        String[] res = instance.toArray(test);
        assertThat(res).hasSameSizeAs(test);
    }

    @Test
    public void shouldAddElementsFromListIntoGivenArrayInTheSamePositionAsInList() {
        List<String> instance = initMyList();
        String[] res = {"new1", "new2", "new3", "new4", "new5"};
        instance.toArray(res);
        assertThat(res).startsWith("test1", "test2", "test3", "test4");
    }

    @Test
    public void shouldSetNullIntoIndexEqualsListSizeIfGivenArrayLengthIsBiggerThanListSize() {
        List<String> instance = initMyList();
        String[] test = {"new1", "new2", "new3", "new4", "new5"};
        instance.toArray(test);
        String res = test[4];
        assertNull(res);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGivenArrayIsNull() {
        List<String> instance = initMyList();
        instance.toArray(null);
    }

    // add(Element el) tests

    @Test
    public void shouldAddElementIntoEndOfList() {
        List<String> instance = initMyList();
        instance.add("new1");
        assertThat(instance).contains("new1", atIndex(4));
    }

    @Test
    public void shouldAddElementIntoListWithIncreasingCapacity() {
        List<String> instance = new MyArrayList<>(0);
        instance.add("new1");
        assertThat(instance).contains("new1", atIndex(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfElementToAddIsNull() {
        List<String> instance = new MyArrayList<>();
        instance.add(null);
    }

    // add(int index, Element el) tests

    @Test
    public void shouldIncreaseListSizeAfterAddingAnElement() {
        List<String> instance = initMyList();
        instance.add("new1");
        int res = instance.size();
        assertEquals(5, res);
    }

    @Test
    public void shouldAddElementIntoRequiredIndex() {
        List<String> instance = initMyList();
        instance.add(2, "new1");
        assertThat(instance).hasSize(5).contains("new1", atIndex(2));
    }

    @Test
    public void shouldAddElementIfIndexForAddingEqualsSize() {
        List<String> instance = initMyList();;
        instance.add(4, "new1");
        assertThat(instance).contains("new1", atIndex(4));
    }

    @Test
    public void shouldIncreaseSizeAfterAddingElementIntoRequiredIndex() {
        List<String> instance = initMyList();;
        instance.add(2, "new1");
        assertThat(instance).hasSize(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfElementToAddIsNullWhenAddWithIndex() {
        List<String> instance = initMyList();;
        instance.add(3, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionWhenIndexForAddingIsLessThenZero() {
        List<String> instance = initMyList();;
        instance.add(-1, "new1");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionWhenIndexForAddingIsBiggerThenSize() {
        List<String> instance = initMyList();;
        instance.add(5, "new1");
    }

    // addAll(Collection Src) test

    @Test
    public void shouldAddAllElementFromSrcCollectionExceptNull() {
        List<String> instance = initMyList();;
        List<String> test = new ArrayList<>(3);
        test.add("new1");
        test.add(null);
        test.add("new2");
        instance.addAll(test);
        assertThat(instance).hasSize(6)
                .contains("new1", atIndex(4))
                .contains("new2", atIndex(5))
                .containsSequence("new1", "new2");
    }

    @Test
    public void shouldReturnTrueIfAddingElementWasSuccessful() {
        List<String> instance = new MyArrayList<>();
        List<String> test = new ArrayList<>(2);
        test.add("new1");
        test.add("new2");
        boolean res = instance.addAll(test);
        assertTrue(res);
    }

    @Test
    public void shouldReturnFalseIfSrcIsEmpty() {
        List<String> instance = new MyArrayList<>();
        List<String> test = new ArrayList<>();
        boolean res = instance.addAll(test);
        assertFalse(res);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSrcIsNull() {
        List<String> instance = new MyArrayList<>();
        instance.addAll(null);
    }

    //  addAll(int index, Collection Src) tests

    @Test
    public void shouldAddAllElementFromSrcCollectionExceptNullIntoRequiredPosition() {
        List<String> instance = initMyList();;
        List<String> test = new ArrayList<>(3);
        test.add("new1");
        test.add(null);
        test.add("new2");
        instance.addAll(2, test);
        assertThat(instance).hasSize(6)
                .contains("new1", atIndex(2))
                .contains("new2", atIndex(3))
                .containsSequence("new1", "new2");
    }

    @Test
    public void shouldReturnTrueIfAddingByIndexWasSuccessful() {
        List<String> instance = initMyList();;
        List<String> test = new ArrayList<>(2);
        test.add("new1");
        test.add("new2");
        boolean res = instance.addAll(2, test);
        assertTrue(res);
    }

    @Test
    public void shouldReturnFalseIfSrcForAddingByIndexIsEmpty() {
        List<String> instance = initMyList();;
        List<String> test = new ArrayList<>();
        boolean res = instance.addAll(2, test);
        assertFalse(res);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenAddWithIndexIfSrcIsNull() {
        List<String> instance = initMyList();;
        instance.addAll(2, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionWhenIndexForAddingSrcIsLessThenZero() {
        List<String> instance = initMyList();;
        List<String> test = new ArrayList<>();
        test.add("new1");
        instance.addAll(-1, test);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionWhenIndexForAddingSrcIsBiggerThenSize() {
        List<String> instance = initMyList();;
        List<String> test = new ArrayList<>();
        test.add("new1");
        instance.addAll(5, test);
    }

    // containsAll() tests

    @Test
    public void shouldReturnTrueIfListContainsAllElementFromSrcCollection() {
        List<String> instance = initMyList();;
        List<String> test = new ArrayList<>();
        test.add("test1");
        test.add("test2");
        boolean res = instance.containsAll(test);
        assertTrue(res);
    }

    @Test
    public void shouldReturnFalseIfListDoesNotContainsAllElementFromSrcCollection() {
        List<String> instance = initMyList();;
        List<String> test = new ArrayList<>();
        test.add("test1");
        test.add("new2");
        boolean res = instance.containsAll(test);
        assertFalse(res);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenSrcCollectionIsNull() {
        List<String> instance = new MyArrayList<>();
        instance.containsAll(null);
    }

    // subList(int startIndex, int endIndex) tests

    @Test
    public void shouldReturnSublistInRangeOfRequiredIndexExcludedEndIndex() {
        List<String> instance = initMyList();;
        List<String> res = instance.subList(1, 3);
        assertThat(res).isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsSequence("test2", "test3");
    }

    @Test
    public void shouldReturnEmptyListIfStartIndexEqualsToEndIndex() {
        List<String> instance = initMyList();;
        List<String> res = instance.subList(3, 3);
        assertThat(res).isNotNull()
                .isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfEndIndexLessThanStart() {
        List<String> instance = initMyList();;
        List<String> res = instance.subList(3, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundExceptionIfStartIndexIsNotInRange() {
        List<String> instance = initMyList();;
        List<String> res = instance.subList(-1, 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundExceptionIfEndIndexIsNotInRange() {
        List<String> instance = initMyList();;
        List<String> res = instance.subList(1, 5);
    }

    // lastIndexOf(Element el) tests

    @Test
    public void shouldReturnLasIndexOfRequiredElement() {
        List<String> instance = new MyArrayList<>();
        instance.add("test1");
        instance.add("test2");
        instance.add("test1");
        int res = instance.lastIndexOf("test1");
        assertEquals(2, res);
    }

    @Test
    public void shouldReturnNegativeNumberOfOneIfListDoesNotContainRequiredElement() {
        List<String> instance = initMyList();;
        int res = instance.lastIndexOf("new1");
        assertEquals(-1, res);
    }

    @Test
    public void shouldReturnNegativeNumberOfOneIfRequiredElementIsNull() {
        List<String> instance = initMyList();;
        int res = instance.lastIndexOf(null);
        assertEquals(-1, res);
    }

    // indexOf(Element el) tests

    @Test
    public void shouldReturnFirstIndexOfRequiredElement() {
        List<String> instance = new MyArrayList<>();
        instance.add("test1");
        instance.add("test2");
        instance.add("test1");
        int res = instance.indexOf("test1");
        assertEquals(0, res);
    }

    @Test
    public void shouldReturnNegativeNumberOfOneIfListDoesNotContainRequiredEl() {
        List<String> instance = initMyList();;
        int res = instance.indexOf("new1");
        assertEquals(-1, res);
    }

    @Test
    public void shouldReturnNegativeNumberOfOneIfRequiredElIsNull() {
        List<String> instance = initMyList();;
        int res = instance.indexOf(null);
        assertEquals(-1, res);
    }

    // get(int index) tests

    @Test
    public void shouldReturnElementByRequiredIndex() {
        List<String> instance = initMyList();;
        String res = instance.get(1);
        assertEquals("test2", res);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundExceptionIfIndexIsNotInRange() {
        List<String> instance = initMyList();;
        String res = instance.get(7);
    }

    // forEach(Consumer<? super T> action) tests

    @Test
    public void shouldApplyRequiredActionForEveryElementInList() {
        List<String> instance = initMyList();;
        StringBuilder builder = new StringBuilder();
        instance.forEach(builder::append);
        String res = builder.toString();
        assertEquals("test1test2test3test4", res);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedOperationExceptionIfStreamWantToChangeOrRewriteElement() {
        List<String> instance = initMyList();;
        instance.replaceAll(el -> el += "23");
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfConsumerIsNull() {
        List<String> instance = initMyList();;
        instance.forEach(null);
    }

    // clone() tests

    @Test
    public void shouldReturnFullCopyOfCurrentList() {
        MyArrayList<String> test = new MyArrayList<>();
        test.add("test1");
        test.add("test2");
        List<String> res = (List<String>) test.clone();
        assertThat(res).isNotNull()
                .isNotEmpty()
                .isNotSameAs(test)
                .hasSize(2)
                .containsSequence("test1", "test2");
    }

    // sort(Comparator<? super T> comparator) tests

    @Test
    public void shouldSortListRequiredToComparator() {
        List<String> instance = initMyList();;
        instance.sort(Comparator.reverseOrder());
        assertThat(instance).isNotNull()
                .isNotEmpty()
                .hasSize(4)
                .containsSequence("test4", "test3", "test2", "test1");
    }

}

