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

        // 入力バイト列に負数を含む場合
        var result2 = parser.parse(ByteSlice.of(new byte[] { (byte) 0x81, (byte) 0x82 }));
        assertEquals(true, result2.isOk());
        assertArrayEquals(new byte[] { (byte) 0x82 }, result2.rest().toByteArray());
    }

    @Test
    void beI16() {
        var parser = Primitive.beI16();
        var result = parser.parse(ByteSlice.of(new byte[] { 0x12, 0x34, 0x56 }));
        assertEquals(true, result.isOk());
        assertArrayEquals(new byte[] { 0x56 }, result.rest().toByteArray());
        assertEquals((short) 0x1234, result.value());

        // 入力バイト列に負数を含む場合
        var result2 = parser.parse(ByteSlice
                .of(new byte[] { (byte) 0x81, (byte) 0x82, (byte) 0x83 }));
        assertEquals(true, result2.isOk());
        assertArrayEquals(new byte[] { (byte) 0x83 }, result2.rest().toByteArray());
        assertEquals((short) 0x8182, result2.value());
    }

    @Test
    void beI32() {
        var parser = Primitive.beI32();
        var result = parser.parse(ByteSlice.of(new byte[] { 0x12, 0x34, 0x56, 0x78, 0x01 }));
        assertEquals(true, result.isOk());
        assertArrayEquals(new byte[] { 0x01 }, result.rest().toByteArray());
        assertEquals(0x12345678, result.value());

        // 入力バイト列に負数を含む場合
        var result2 = parser
                .parse(ByteSlice.of(new byte[] { (byte) 0x81, (byte) 0x82, (byte) 0x83, (byte) 0x84, (byte) 0x85 }));
        assertEquals(true, result2.isOk());
        assertArrayEquals(new byte[] { (byte) 0x85 }, result2.rest().toByteArray());
        assertEquals(0x81828384, result2.value());
    }

    @Test
    void beI64() {
        var parser = Primitive.beI64();
        var result = parser.parse(ByteSlice.of(new byte[] { 0x12, 0x34, 0x56, 0x78,
                0x01, 0x23, 0x45, 0x67, 0x02 }));
        assertEquals(true, result.isOk());
        assertArrayEquals(new byte[] { 0x02 }, result.rest().toByteArray());
        assertEquals(0x1234567801234567L, result.value());

        // 入力バイト列に負数を含む場合
        var result2 = parser.parse(ByteSlice.of(new byte[] { (byte) 0x81, (byte) 0x82, (byte) 0x83, (byte) 0x84,
                (byte) 0x85, (byte) 0x86, (byte) 0x87, (byte) 0x88, (byte) 0x89 }));
        assertEquals(true, result2.isOk());
        assertArrayEquals(new byte[] { (byte) 0x89 }, result2.rest().toByteArray());
        assertEquals(0x8182838485868788L, result2.value());
    }

    @Test
    void test_t1ag() {
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
