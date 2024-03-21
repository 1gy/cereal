package io.github._1gy.cereal.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ResultTest {
    @Test
    void itWorks() {
        var result1 = Result.ok("value");
        var text1 = switch (result1) {
            case Result.Ok<String, ?> ok -> ok.unwrap();
            case Result.Err<?, ?> err -> "err";
        };
        assertEquals("value", text1);

        var result2 = Result.err("error");
        var text2 = switch (result2) {
            case Result.Ok<?, ?> ok -> "ok";
            case Result.Err<?, String> err -> err.unwrapErr();
        };
        assertEquals("error", text2);
    }

    @Test
    void test_isOk() {
        var result1 = Result.ok("value");
        assertEquals(true, result1.isOk());

        var result2 = Result.err("error");
        assertEquals(false, result2.isOk());
    }

    @Test
    void test_isErr() {
        var result1 = Result.ok("value");
        assertEquals(false, result1.isErr());

        var result2 = Result.err("error");
        assertEquals(true, result2.isErr());
    }

    @Test
    void test_value() {
        var result1 = Result.ok("value");
        assertEquals("value", result1.unwrap());

        var result2 = Result.err("error");
        assertThrows(IllegalStateException.class, () -> result2.unwrap());
    }

    @Test
    void test_error() {
        var result1 = Result.ok("value");
        assertThrows(IllegalStateException.class, () -> result1.unwrapErr());

        var result2 = Result.err("error");
        assertEquals("error", result2.unwrapErr());
    }
}
