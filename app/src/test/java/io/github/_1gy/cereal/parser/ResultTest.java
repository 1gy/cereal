package io.github._1gy.cereal.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    void test_unwrap() {
        var result1 = Result.ok("value");
        assertEquals("value", result1.unwrap());

        var result2 = Result.err("error");
        assertThrows(IllegalStateException.class, () -> result2.unwrap());
    }

    @Test
    void test_unwrapErr() {
        var result1 = Result.ok("value");
        assertThrows(IllegalStateException.class, () -> result1.unwrapErr());

        var result2 = Result.err("error");
        assertEquals("error", result2.unwrapErr());
    }

    @Test
    void test_map() {
        var result1 = Result.ok("value");
        var result2 = result1.map(String::length);
        assertEquals(5, result2.unwrap());

        Result<String, ?> result3 = Result.err("error");
        var result4 = result3.map(String::length);
        assertEquals("error", result4.unwrapErr());
    }

    @Test
    void test_andThen() {
        var result1 = Result.ok("value");
        var result2 = result1.andThen(value -> Result.ok(value.length()));
        assertEquals(5, result2.unwrap());

        Result<String, ?> result3 = Result.err("error");
        var result4 = result3.andThen(value -> Result.ok(value.length()));
        assertEquals("error", result4.unwrapErr());
    }

    @Test
    void test_ok_hashCode() {
        var result1 = Result.ok("value");
        var result2 = Result.ok("value");
        assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void test_err_hashCode() {
        var result1 = Result.err("error");
        var result2 = Result.err("error");
        assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void test_ok_equals() {
        var result1 = Result.ok("value");
        var result2 = Result.ok("value");
        assertEquals(result1, result2);

        var result3 = Result.ok("value1");
        var result4 = Result.ok("value2");
        assertNotEquals(result3, result4);

        var result5 = Result.ok("value");
        var result6 = Result.err("error");
        assertNotEquals(result5, result6);
    }

    @Test
    void test_err_equals() {
        var result1 = Result.err("error");
        var result2 = Result.err("error");
        assertEquals(result1, result2);

        var result3 = Result.err("error1");
        var result4 = Result.err("error2");
        assertNotEquals(result3, result4);

        var result5 = Result.err("error");
        var result6 = Result.ok("value");
        assertNotEquals(result5, result6);
    }

    @Test
    void test_ok_toString() {
        var result = Result.ok("value");
        assertEquals("Ok [value=value]", result.toString());
    }

    @Test
    void test_err_toString() {
        var result = Result.err("error");
        assertEquals("Err [error=error]", result.toString());
    }
}
