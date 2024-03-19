package io.github._1gy.cereal.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ByteSliceTest {
    @Test
    void testOf() {
        final var bytes = new byte[] { 0, 1, 2, 3, 4, 5 };
        final var slice = ByteSlice.of(bytes);
        assertEquals(6, slice.length());
        assertEquals(0, slice.get(0));
        assertEquals(1, slice.get(1));
        assertEquals(2, slice.get(2));
        assertEquals(3, slice.get(3));
        assertEquals(4, slice.get(4));
        assertEquals(5, slice.get(5));
    }

    @Test
    void testOf_subslice() {
        final var bytes = new byte[] { 0, 1, 2, 3, 4, 5 };
        final var slice = ByteSlice.of(bytes);

        // [0, 1, 2, 3, 4, 5](1..4) = [_, 1, 2, 3, _, _]
        final var subslice1 = ByteSlice.of(slice, Range.of(1, 4));
        assertEquals(3, subslice1.length());
        assertEquals(1, subslice1.get(0));
        assertEquals(2, subslice1.get(1));
        assertEquals(3, subslice1.get(2));

        // [0, 1, 2, 3, 4, 5](..3) = [0, 1, 2, _, _, _]
        final var subslice2 = ByteSlice.of(slice, Range.to(3));
        assertEquals(3, subslice2.length());
        assertEquals(0, subslice2.get(0));
        assertEquals(1, subslice2.get(1));
        assertEquals(2, subslice2.get(2));

        // [0, 1, 2, 3, 4, 5](3..) = [_, _, _, 3, 4, 5]
        final var subslice3 = ByteSlice.of(slice, Range.from(3));
        assertEquals(3, subslice3.length());
        assertEquals(3, subslice3.get(0));
        assertEquals(4, subslice3.get(1));
        assertEquals(5, subslice3.get(2));

        // [0, 1, 2, 3, 4, 5](1..4) = [_, 1, 2, 3, _, _]
        // [1, 2, 3](..2) = [1, 2, _]
        final var subslice4 = ByteSlice.of(subslice1, Range.to(2));
        assertEquals(2, subslice4.length());
        assertEquals(1, subslice4.get(0));
        assertEquals(2, subslice4.get(1));
    }

    @Test
    void testOf_ByteSlice_invalidRange() {
        final var bytes = new byte[] { 0, 1, 2, 3, 4, 5 };

        // [0, 1, 2, 3, 4, 5](1..5) = [_, 1, 2, 3, 4, _]
        final var slice = ByteSlice.of(bytes, Range.of(1, 5));

        assertThrows(IllegalArgumentException.class, () -> ByteSlice.of(slice, Range.from(-1)));
        assertArrayEquals(new byte[] { 1, 2, 3, 4 }, ByteSlice.of(slice, Range.from(0)).toByteArray());
        assertArrayEquals(new byte[] { 2, 3, 4 }, ByteSlice.of(slice, Range.from(1)).toByteArray());
        assertArrayEquals(new byte[] { 3, 4 }, ByteSlice.of(slice, Range.from(2)).toByteArray());
        assertArrayEquals(new byte[] { 4 }, ByteSlice.of(slice, Range.from(3)).toByteArray());
        assertArrayEquals(new byte[] {}, ByteSlice.of(slice, Range.from(4)).toByteArray());
        assertThrows(IllegalArgumentException.class, () -> ByteSlice.of(slice, Range.from(5)));

        assertThrows(IllegalArgumentException.class, () -> ByteSlice.of(slice, Range.to(-1)));
        assertArrayEquals(new byte[] {}, ByteSlice.of(slice, Range.to(0)).toByteArray());
        assertArrayEquals(new byte[] { 1, }, ByteSlice.of(slice, Range.to(1)).toByteArray());
        assertArrayEquals(new byte[] { 1, 2, }, ByteSlice.of(slice, Range.to(2)).toByteArray());
        assertArrayEquals(new byte[] { 1, 2, 3, }, ByteSlice.of(slice, Range.to(3)).toByteArray());
        assertArrayEquals(new byte[] { 1, 2, 3, 4 }, ByteSlice.of(slice, Range.to(4)).toByteArray());
        assertThrows(IllegalArgumentException.class, () -> ByteSlice.of(slice, Range.to(5)));
    }

    @Test
    void testOf_ByteSlice_nullcheck() {
        final ByteSlice slice = null;
        assertThrows(NullPointerException.class, () -> ByteSlice.of(slice, Range.of(0, 1)));
    }

    @Test
    void testOf_ByteArray_invalidRange() {
        final var bytes = new byte[] { 1, 2, 3, 4 };

        assertThrows(IllegalArgumentException.class, () -> ByteSlice.of(bytes, Range.from(-1)));
        assertArrayEquals(new byte[] { 1, 2, 3, 4 }, ByteSlice.of(bytes, Range.from(0)).toByteArray());
        assertArrayEquals(new byte[] { 2, 3, 4 }, ByteSlice.of(bytes, Range.from(1)).toByteArray());
        assertArrayEquals(new byte[] { 3, 4 }, ByteSlice.of(bytes, Range.from(2)).toByteArray());
        assertArrayEquals(new byte[] { 4 }, ByteSlice.of(bytes, Range.from(3)).toByteArray());
        assertArrayEquals(new byte[] {}, ByteSlice.of(bytes, Range.from(4)).toByteArray());
        assertThrows(IllegalArgumentException.class, () -> ByteSlice.of(bytes, Range.from(5)));

        assertThrows(IllegalArgumentException.class, () -> ByteSlice.of(bytes, Range.to(-1)));
        assertArrayEquals(new byte[] {}, ByteSlice.of(bytes, Range.to(0)).toByteArray());
        assertArrayEquals(new byte[] { 1, }, ByteSlice.of(bytes, Range.to(1)).toByteArray());
        assertArrayEquals(new byte[] { 1, 2, }, ByteSlice.of(bytes, Range.to(2)).toByteArray());
        assertArrayEquals(new byte[] { 1, 2, 3, }, ByteSlice.of(bytes, Range.to(3)).toByteArray());
        assertArrayEquals(new byte[] { 1, 2, 3, 4 }, ByteSlice.of(bytes, Range.to(4)).toByteArray());
        assertThrows(IllegalArgumentException.class, () -> ByteSlice.of(bytes, Range.to(5)));
    }

    @Test
    void testOf_ByteArray_nullcheck() {
        final byte[] bytes = null;
        assertThrows(NullPointerException.class, () -> ByteSlice.of(bytes));
        assertThrows(NullPointerException.class, () -> ByteSlice.of(bytes, Range.of(0, 1)));
    }

    @Test
    void testToByteArray() {
        final var bytes = new byte[] { 0, 1, 2, 3, 4, 5 };
        final var slice = ByteSlice.of(bytes);
        assertArrayEquals(bytes, slice.toByteArray());

        final var subslice = ByteSlice.of(slice, Range.of(1, 4));
        assertArrayEquals(new byte[] { 1, 2, 3 }, subslice.toByteArray());
    }

    @Test
    void testLength() {
        final var bytes = new byte[] { 0, 1, 2, 3, 4, 5 };
        final var slice = ByteSlice.of(bytes);
        assertEquals(6, slice.length());

        final var subslice = ByteSlice.of(slice, Range.of(1, 4));
        assertEquals(3, subslice.length());
    }

    @Test
    void testGet() {
        final var bytes = new byte[] { 0, 1, 2, 3, 4, 5 };
        final var slice = ByteSlice.of(bytes);
        assertThrows(IndexOutOfBoundsException.class, () -> slice.get(-1));
        assertEquals(0, slice.get(0));
        assertEquals(1, slice.get(1));
        assertEquals(2, slice.get(2));
        assertEquals(3, slice.get(3));
        assertEquals(4, slice.get(4));
        assertEquals(5, slice.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> slice.get(6));

        final var subslice = ByteSlice.of(slice, Range.of(1, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> subslice.get(-1));
        assertEquals(1, subslice.get(0));
        assertEquals(2, subslice.get(1));
        assertEquals(3, subslice.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> subslice.get(3));
    }
}
