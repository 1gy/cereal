package io.github._1gy.cereal.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CombinatorTest {

    @Test
    void test_map() {
        var num = Combinator.map(Primitive.beI16(), v -> "#" + String.valueOf(v));
        var input = ByteSlice.of(new byte[] { 0x01, 0x02, 0x03 });
        var result = num.parse(input);

        assertEquals(result.isOk(), true);
        assertEquals(result.unwrap().value(), "#258");
        assertArrayEquals(result.unwrap().rest().toByteArray(), new byte[] { 0x03 });
    }

    @Test
    void test_iterator() {
        var num = Primitive.beI16();
        var nums = Combinator.iterator(num);
        var input = ByteSlice.of(new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05 });
        var result = nums.parse(input);

        assertEquals(result.isOk(), true);
        assertEquals(result.unwrap().value().size(), 2);
        assertEquals(result.unwrap().value().get(0), (short) 0x0102);
        assertEquals(result.unwrap().value().get(1), (short) 0x0304);
        assertArrayEquals(result.unwrap().rest().toByteArray(), new byte[] { 0x05 });
    }
}
