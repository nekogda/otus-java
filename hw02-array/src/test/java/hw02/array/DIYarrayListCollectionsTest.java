package hw02.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Collections должен")
public class DIYarrayListCollectionsTest {

    private DIYarrayList<Integer> a;

    @BeforeEach
    public void setUp() {
        a = new DIYarrayList<>(2);
    }

    @DisplayName("добавлять элементы в коллекцию при вызове addAll")
    @Test
    public void testAddAllWithoutResize() {
        Collections.addAll(a, 10, 20);
        assertEquals(2, a.size());
    }

    @DisplayName("добавлять элементы в коллекцию и вызывать реаллокацию при вызове addAll")
    @Test
    public void testAddAllWithResize() {
        Collections.addAll(a, 10, 20, 30, 40);
        assertEquals(4, a.size());
    }

    @DisplayName("при вызове copy скопировать элементы в dst список большего размера")
    @Test
    public void testCopyDstGt() {
        Collections.addAll(a, 1, 2);
        DIYarrayList<Integer> b = new DIYarrayList<>();
        Collections.addAll(b, 7, 7, 7);
        Collections.copy(b, a);
        assertTrue(Arrays.equals(a.toArray(), 0, a.size(), b.toArray(), 0, a.size()));
        assertEquals(7, b.get(2));
    }

    @DisplayName("при вызове copy скопировать элементы в dst список такого же размера")
    @Test
    public void testCopyDstEquale() {
        Collections.addAll(a, 1, 2, 3);
        DIYarrayList<Integer> b = new DIYarrayList<>();
        Collections.addAll(b, 7, 7, 7);
        Collections.copy(b, a);
        assertArrayEquals(a.toArray(), b.toArray());
    }

    @DisplayName("выбросить исключение при вызове copy если dst список меньше исходного")
    @Test
    public void testCopyDstLt() {
        Collections.addAll(a, 1, 2, 3);
        DIYarrayList<Integer> b = new DIYarrayList<>();
        Collections.addAll(b, 7, 7);
        assertThrows(IndexOutOfBoundsException.class, () -> Collections.copy(b, a));
    }

    @DisplayName("отсортировать список при вызове sort")
    @Test
    public void testSort() {
        Collections.addAll(a, 5, 4, 2, 3);
        Collections.sort(a);
        assertArrayEquals(new Object[]{2, 3, 4, 5}, a.toArray());
    }
}
