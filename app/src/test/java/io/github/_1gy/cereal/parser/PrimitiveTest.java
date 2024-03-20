package io.github._1gy.cereal.parser;

import static io.github._1gy.cereal.parser.Primitive.tag;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrimitiveTest {

    @Test
    void test_tag() {
        var parser = tag("abc".getBytes());
        var result = parser.parse(ByteSlice.of("abcdef".getBytes()));
        assertEquals(true, result.isOk());
        assertArrayEquals("def".getBytes(), result.rest().toByteArray());
        assertArrayEquals("abc".getBytes(), result.value().toByteArray());

        var result2 = parser.parse(ByteSlice.of("def".getBytes()));
        assertEquals(false, result2.isOk());

        var result3 = parser.parse(ByteSlice.of("ab".getBytes()));
        assertEquals(false, result3.isOk());
    }
}
