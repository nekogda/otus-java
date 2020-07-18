package hw08.serialization;

import com.google.gson.Gson;
import hw08.serialization.examples.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
