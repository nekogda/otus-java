package hw02.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Collections должен")
public class DIYarrayListCollectionsTest {

    private DIYarrayList<Integer> arrayList;

    @BeforeEach
    public void setUp() {
        arrayList = new DIYarrayList<>(2);
    }

    @DisplayName("добавлять элементы в коллекцию при вызове addAll")
    @Test
    public void testAddAllWithoutResize() {
        Collections.addAll(arrayList, 10, 20);
        assertEquals(2, arrayList.size());
    }

    @DisplayName("добавлять элементы в коллекцию и вызывать реаллокацию при вызове addAll")
    @Test
    public void testAddAllWithResize() {
        Collections.addAll(arrayList, 10, 20, 30, 40);
        assertEquals(4, arrayList.size());
    }

    @DisplayName("при вызове copy скопировать элементы в dst список большего размера")
    @Test
    public void testCopyDstGt() {
        Collections.addAll(arrayList, 1, 2);
        DIYarrayList<Integer> b = new DIYarrayList<>();
        Collections.addAll(b, 7, 7, 7);
        Collections.copy(b, arrayList);
        assertTrue(Arrays.equals(arrayList.toArray(), 0, arrayList.size(), b.toArray(), 0, arrayList.size()));
        assertEquals(7, b.get(2));
    }

    @DisplayName("при вызове copy скопировать элементы в dst список такого же размера")
    @Test
    public void testCopyDstEquale() {
        Collections.addAll(arrayList, 1, 2, 3);
        DIYarrayList<Integer> b = new DIYarrayList<>();
        Collections.addAll(b, 7, 7, 7);
        Collections.copy(b, arrayList);
        assertArrayEquals(arrayList.toArray(), b.toArray());
    }

    @DisplayName("выбросить исключение при вызове copy если dst список меньше исходного")
    @Test
    public void testCopyDstLt() {
        Collections.addAll(arrayList, 1, 2, 3);
        DIYarrayList<Integer> b = new DIYarrayList<>();
        Collections.addAll(b, 7, 7);
        assertThrows(IndexOutOfBoundsException.class, () -> Collections.copy(b, arrayList));
    }

    @DisplayName("отсортировать список при вызове sort")
    @Test
    public void testSort() {
        Collections.addAll(arrayList, 5, 4, 2, 3);
        Collections.sort(arrayList);
        assertArrayEquals(new Object[]{2, 3, 4, 5}, arrayList.toArray());
    }
}
