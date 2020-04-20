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

    private DIYarrayList<Integer> arrayList;

    @BeforeEach
    void setUp() {
        arrayList = new DIYarrayList<>(5);
    }

    @DisplayName("выбросить исключение при вызове get с индексом вне диапазона")
    @Test
    void getWithException() {
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(-1));
    }

    @DisplayName("вернуть элемент коллекции при вызове get")
    @Test
    void getElement() {
        arrayList.add(1);
        assertEquals(1, arrayList.get(0));
    }

    @DisplayName("обновить элемент при вызове set")
    @Test
    void setElement() {
        arrayList.add(1);
        assertEquals(1, arrayList.set(0, 2));
        assertEquals(2, arrayList.get(0));
    }

    @DisplayName("выбросить исключение при вызове set с индексом вне диапазона")
    @Test
    void setWithException() {
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.set(0, 1));
        arrayList.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.set(1, 1));
    }

    @DisplayName("вернуть текстовое представление объекта при вызове toString")
    @Test
    void testToString() {
        assertEquals("DIYarrayList[]", arrayList.toString());
        arrayList.add(1);
        assertEquals("DIYarrayList[1]", arrayList.toString());
        arrayList.add(2);
        assertEquals("DIYarrayList[1, 2]", arrayList.toString());
    }

    @DisplayName("вернуть true при сравнении объектов с одинаковым содержимым")
    @Test
    void testEquals() {
        DIYarrayList<Integer> b = new DIYarrayList<>(2);
        assertEquals(arrayList, b);
        arrayList.add(1);
        b.add(1);
        assertEquals(arrayList, b);
    }

    @DisplayName("вернуть false при сравнении объектов с разным содержимым")
    @Test
    void testNotEquals() {
        DIYarrayList<Integer> b = new DIYarrayList<>(2);
        arrayList.add(1);
        b.add(2);
        assertNotEquals(arrayList, b);
    }

    @DisplayName("возвращать одинаковый hashCode для одинаковых объектов")
    @Test
    void testHashCode() {
        DIYarrayList<Integer> b = new DIYarrayList<>(2);
        assertEquals(arrayList.hashCode(), b.hashCode());
        assertEquals(arrayList, b);
        b.add(1);
        arrayList.add(1);
        assertEquals(arrayList.hashCode(), b.hashCode());
        assertEquals(arrayList, b);
    }

    @DisplayName("возвращать отличающийся hashCode для разных объектов")
    @Test
    void testHashCodeNotEquals() {
        DIYarrayList<Integer> b = new DIYarrayList<>(2);
        b.add(1);
        arrayList.add(2);
        assertNotEquals(arrayList.hashCode(), b.hashCode());
        assertNotEquals(arrayList, b);
    }

    @DisplayName("возвращать другой hashCode после изменения состояния объекта")
    @Test
    void testHashCodeChanging() {
        arrayList.add(2);
        int currHash = arrayList.hashCode();
        arrayList.set(0, 1);
        int newHash = arrayList.hashCode();
        assertNotEquals(currHash, newHash);
        arrayList.add(2);
        assertNotEquals(newHash, arrayList.hashCode());
    }

    @DisplayName("возвращать размер объекта при вызове size")
    @Test
    void size() {
        assertEquals(0, arrayList.size());
        arrayList.add(1);
        assertEquals(1, arrayList.size());
    }

    @DisplayName("возвращать true в случае пустой коллекции")
    @Test
    void isEmpty() {
        assertTrue(arrayList.isEmpty());
        arrayList.add(1);
        assertFalse(arrayList.isEmpty());
    }

    @DisplayName("при вызове add добавить элемент")
    @Test
    void add() {
        assertTrue(arrayList.add(1));
        assertEquals(1, arrayList.get(0));
    }

    @DisplayName("при вызове add добавить элемент в коллекцию привысив начальную емкость")
    @Test
    void addWithResize() {
        for (int i = 0; i < 5; ++i) {
            arrayList.add(i);
        }
        arrayList.add(6);
        assertEquals(6, arrayList.get(5));
        assertEquals(6, arrayList.size());
    }

    @DisplayName("при вызове toArray возвращать копию массива")
    @Test
    void toArray() {
        arrayList.add(4);
        Object[] cp = arrayList.toArray();
        assertEquals(arrayList.get(0), (Integer) cp[0]);
        cp[0] = 1;
        assertNotEquals(arrayList.get(0), (Integer) cp[0]);
    }

    @DisplayName("вернуть итератор при вызве listIterator")
    @Test
    void listIterator() {
        ListIterator<Integer> i = arrayList.listIterator();
        assertNotNull(i);
    }

    @Nested
    @DisplayName("ListIterator должен")
    class ListIteratorTest {

        @DisplayName("возвращать false при вызове hasNext на исчерпавшемся итераторе")
        @Test
        void listIteratorHasNextOnEmptyArray() {
            ListIterator<Integer> i = arrayList.listIterator();
            assertFalse(i.hasNext());
        }

        @DisplayName("выбрасывать исключение при вызове next на пустой коллекции")
        @Test
        void listIteratorOnEmptyArrayWithException() {
            ListIterator<Integer> i = arrayList.listIterator();
            assertThrows(NoSuchElementException.class, i::next);
        }

        @DisplayName("выбрасывать исключение при вызове next на исчерпавшемся итераторе")
        @Test
        void listIteratorExceptionAtTheEnd() {
            arrayList.add(1);
            ListIterator<Integer> i = arrayList.listIterator();
            assertEquals(1, i.next());
            assertThrows(NoSuchElementException.class, i::next);
        }

        @DisplayName("возвращать элементы коллекции при вызове next")
        @Test
        void listIteratorWithElements() {
            arrayList.add(1);
            arrayList.add(2);
            ListIterator<Integer> i = arrayList.listIterator();
            assertTrue(i.hasNext());
            assertEquals(1, i.next());
            assertTrue(i.hasNext());
            assertEquals(2, i.next());
            assertFalse(i.hasNext());
        }
    }
}