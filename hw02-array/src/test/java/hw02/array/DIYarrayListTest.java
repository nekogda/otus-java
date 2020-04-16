package hw02.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DIYarrayList должен")
class DIYarrayListTest {

    private DIYarrayList<Integer> a;

    @BeforeEach
    void setUp() {
        a = new DIYarrayList<>(5);
    }

    @DisplayName("выбросить исключение при вызове get с индексом вне диапазона")
    @Test
    void getWithException() {
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(-1));
    }

    @DisplayName("вернуть элемент коллекции при вызове get")
    @Test
    void getElement() {
        a.add(1);
        assertEquals(1, a.get(0));
    }

    @DisplayName("обновить элемент при вызове set")
    @Test
    void setElement() {
        a.add(1);
        assertEquals(1, a.set(0, 2));
        assertEquals(2, a.get(0));
    }

    @DisplayName("выбросить исключение при вызове set с индексом вне диапазона")
    @Test
    void setWithException() {
        assertThrows(IndexOutOfBoundsException.class, () -> a.set(0, 1));
        a.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> a.set(1, 1));
    }

    @DisplayName("вернуть текстовое представление объекта при вызове toString")
    @Test
    void testToString() {
        assertEquals("DIYarrayList[]", a.toString());
        a.add(1);
        assertEquals("DIYarrayList[1]", a.toString());
        a.add(2);
        assertEquals("DIYarrayList[1, 2]", a.toString());
    }

    @DisplayName("вернуть true при сравнении объектов с одинаковым содержимым")
    @Test
    void testEquals() {
        DIYarrayList<Integer> b = new DIYarrayList<>(2);
        assertEquals(a, b);
        a.add(1);
        b.add(1);
        assertEquals(a, b);
    }

    @DisplayName("вернуть false при сравнении объектов с разным содержимым")
    @Test
    void testNotEquals() {
        DIYarrayList<Integer> b = new DIYarrayList<>(2);
        a.add(1);
        b.add(2);
        assertNotEquals(a, b);
    }

    @DisplayName("возвращать одинаковый hashCode для одинаковых объектов")
    @Test
    void testHashCode() {
        DIYarrayList<Integer> b = new DIYarrayList<>(2);
        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a, b);
        b.add(1);
        a.add(1);
        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a, b);
    }

    @DisplayName("возвращать отличающийся hashCode для разных объектов")
    @Test
    void testHashCodeNotEquals() {
        DIYarrayList<Integer> b = new DIYarrayList<>(2);
        b.add(1);
        a.add(2);
        assertNotEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, b);
    }

    @DisplayName("возвращать другой hashCode после изменения состояния объекта")
    @Test
    void testHashCodeChanging() {
        a.add(2);
        int currHash = a.hashCode();
        a.set(0, 1);
        int newHash = a.hashCode();
        assertNotEquals(currHash, newHash);
        a.add(2);
        assertNotEquals(newHash, a.hashCode());
    }

    @DisplayName("возвращать размер объекта при вызове size")
    @Test
    void size() {
        assertEquals(0, a.size());
        a.add(1);
        assertEquals(1, a.size());
    }

    @DisplayName("возвращать true в случае пустой коллекции")
    @Test
    void isEmpty() {
        assertTrue(a.isEmpty());
        a.add(1);
        assertFalse(a.isEmpty());
    }

    @DisplayName("при вызове add добавить элемент")
    @Test
    void add() {
        assertTrue(a.add(1));
        assertEquals(1, a.get(0));
    }

    @DisplayName("при вызове add добавить элемент в коллекцию привысив начальную емкость")
    @Test
    void addWithResize() {
        for (int i = 0; i < 5; ++i) {
            a.add(i);
        }
        a.add(6);
        assertEquals(6, a.get(5));
        assertEquals(6, a.size());
    }

    @DisplayName("при вызове toArray возвращать копию массива")
    @Test
    void toArray() {
        a.add(4);
        Object[] cp = a.toArray();
        assertEquals(a.get(0), (Integer) cp[0]);
        cp[0] = 1;
        assertNotEquals(a.get(0), (Integer) cp[0]);
    }

    @DisplayName("вернуть итератор при вызве listIterator")
    @Test
    void listIterator() {
        ListIterator<Integer> i = a.listIterator();
        assertNotNull(i);
    }

    @Nested
    @DisplayName("ListIterator должен")
    class ListIteratorTest {

        @DisplayName("возвращать false при вызове hasNext на исчерпавшемся итераторе")
        @Test
        void listIteratorHasNextOnEmptyArray() {
            ListIterator<Integer> i = a.listIterator();
            assertFalse(i.hasNext());
        }

        @DisplayName("выбрасывать исключение при вызове next на пустой коллекции")
        @Test
        void listIteratorOnEmptyArrayWithException() {
            ListIterator<Integer> i = a.listIterator();
            assertThrows(NoSuchElementException.class, i::next);
        }

        @DisplayName("выбрасывать исключение при вызове next на исчерпавшемся итераторе")
        @Test
        void listIteratorExceptionAtTheEnd() {
            a.add(1);
            ListIterator<Integer> i = a.listIterator();
            assertEquals(1, i.next());
            assertThrows(NoSuchElementException.class, i::next);
        }

        @DisplayName("возвращать элементы коллекции при вызове next")
        @Test
        void listIteratorWithElements() {
            a.add(1);
            a.add(2);
            ListIterator<Integer> i = a.listIterator();
            assertTrue(i.hasNext());
            assertEquals(1, i.next());
            assertTrue(i.hasNext());
            assertEquals(2, i.next());
            assertFalse(i.hasNext());
        }
    }
}