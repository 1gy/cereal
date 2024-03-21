package io.github._1gy.cereal.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    void itWorks() {
        Parser<ByteSlice, Integer, String> digit = input -> {
            var c = input.get(0);
            if (c >= '0' && c <= '9') {
                return Result.ok(ParsedValue.of(input.slice(Range.from(1)), (int) c - '0'));
            } else {
                return Result.err("not a digit");
            }
        };
        var result = digit.parse(ByteSlice.of("123".getBytes()));
        assertEquals(true, result.isOk());
        assertEquals(1, result.unwrap().value());
        assertEquals("ByteSlice[1..3]", result.unwrap().rest().toString());
    }
}
