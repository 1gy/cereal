package io.github._1gy.cereal.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ResultTest {
    @Test
    void itWorks() {
        var result1 = Result.ok("rest", "value");
        var text1 = switch (result1) {
            case Result.Ok<String, String, ?> ok -> ok.rest() + " " + ok.value();
            case Result.Err<?, ?, ?> err -> "err";
        };
        assertEquals("rest value", text1);

        var result2 = Result.err("error");
        var text2 = switch (result2) {
            case Result.Ok<?, ?, ?> ok -> "ok";
            case Result.Err<?, ?, String> err -> err.error();
        };
        assertEquals("error", text2);
    }

    @Test
    void test_isOk() {
        var result1 = Result.ok("rest", "value");
        assertEquals(true, result1.isOk());

        var result2 = Result.err("error");
        assertEquals(false, result2.isOk());
    }

    @Test
    void test_isErr() {
        var result1 = Result.ok("rest", "value");
        assertEquals(false, result1.isErr());

        var result2 = Result.err("error");
        assertEquals(true, result2.isErr());
    }

    @Test
    void test_rest() {
        var result1 = Result.ok("rest", "value");
        assertEquals("rest", result1.rest());

        var result2 = Result.err("error");
        assertThrows(IllegalStateException.class, () -> result2.rest());
    }

    @Test
    void test_value() {
        var result1 = Result.ok("rest", "value");
        assertEquals("value", result1.value());

        var result2 = Result.err("error");
        assertThrows(IllegalStateException.class, () -> result2.value());
    }

    @Test
    void test_error() {
        var result1 = Result.ok("rest", "value");
        assertThrows(IllegalStateException.class, () -> result1.error());

        var result2 = Result.err("error");
        assertEquals("error", result2.error());
    }
}
