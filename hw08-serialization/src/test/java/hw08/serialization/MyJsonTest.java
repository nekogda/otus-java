package hw08.serialization;

import com.google.gson.Gson;
import hw08.serialization.examples.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MyJsonTest {
    private MyJson myJson;
    private Gson gson;

    @BeforeEach
    void setup() {
        myJson = new MyJson();
        gson = new Gson();
    }

    @Test
    @DisplayName("serialize scalar objects to json")
    void scalarTest() {
        assertEquals(gson.toJson(null), myJson.toJson(null));
        assertEquals(gson.toJson((byte) 1), myJson.toJson((byte) 1));
        assertEquals(gson.toJson((short) 1f), myJson.toJson((short) 1f));
        assertEquals(gson.toJson(1), myJson.toJson(1));
        assertEquals(gson.toJson(1L), myJson.toJson(1L));
        assertEquals(gson.toJson(1d), myJson.toJson(1d));
        assertEquals(gson.toJson("aaa"), myJson.toJson("aaa"));
        assertEquals(gson.toJson('a'), myJson.toJson('a'));
        assertEquals(gson.toJson(new int[]{1, 2, 3}), myJson.toJson(new int[]{1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2, 3)), myJson.toJson(List.of(1, 2, 3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), myJson.toJson(Collections.singletonList(1)));
    }

    @Test
    @DisplayName("serialize flat object to json")
    void primitiveExampleTest() {
        var example = new PrimitiveExample();
        var json = myJson.toJson(example);
        assertEquals(example, gson.fromJson(json, PrimitiveExample.class));
    }

    @Test
    @DisplayName("serialize class with compound field to json")
    void withClassExampleTest() {
        var example = new WithClassExample();
        var json = myJson.toJson(example);
        assertEquals(example, gson.fromJson(json, WithClassExample.class));
    }

    @Test
    @DisplayName("serialize class with field of collection type to json")
    void collectionExampleTest() {
        var example = new CollectionExample();
        var json = myJson.toJson(example);
        assertEquals(example, gson.fromJson(json, CollectionExample.class));
    }

    @Test
    @DisplayName("serialize class with field of array type to json")
    void arrayExampleTest() {
        var example = new ArrayExample();
        var json = myJson.toJson(example);
        assertEquals(example, gson.fromJson(json, ArrayExample.class));
    }

    @Test
    @DisplayName("serialize class with string field to json")
    void referenceExampleTest() {
        var example = new StringExample();
        var json = myJson.toJson(example);
        assertEquals(example, gson.fromJson(json, StringExample.class));
    }

    @Test
    @DisplayName("serialize null to json")
    void nullExampleTest() {
        var result = myJson.toJson(null);
        assertNull(gson.fromJson(result, Object.class));
    }
}
