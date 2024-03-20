package io.github._1gy.cereal.parser;

import static io.github._1gy.cereal.parser.Primitive.tag;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrimitiveTest {

    @Test
    void beI8() {
        var parser = Primitive.beI8();
        var result = parser.parse(ByteSlice.of(new byte[] { 0x12, 0x34 }));
        assertEquals(true, result.isOk());
        assertArrayEquals(new byte[] { 0x34 }, result.rest().toByteArray());
        assertEquals((byte) 0x12, result.value());
    }

    @Test
    void beI16() {
        var parser = Primitive.beI16();
        var result = parser.parse(ByteSlice.of(new byte[] { 0x12, 0x34, 0x56 }));
        assertEquals(true, result.isOk());
        assertArrayEquals(new byte[] { 0x56 }, result.rest().toByteArray());
        assertEquals((short) 0x1234, result.value());
    }

    @Test
    void beI32() {
        var parser = Primitive.beI32();
        var result = parser.parse(ByteSlice.of(new byte[] { 0x12, 0x34, 0x56, 0x78, 0x01 }));
        assertEquals(true, result.isOk());
        assertArrayEquals(new byte[] { 0x01 }, result.rest().toByteArray());
        assertEquals(0x12345678, result.value());
    }

    @Test
    void beI64() {
        var parser = Primitive.beI64();
        var result = parser.parse(ByteSlice.of(new byte[] { 0x12, 0x34, 0x56, 0x78, 0x01, 0x23, 0x45, 0x67, 0x02 }));
        assertEquals(true, result.isOk());
        assertArrayEquals(new byte[] { 0x02 }, result.rest().toByteArray());
        assertEquals(0x1234567801234567L, result.value());
    }

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
