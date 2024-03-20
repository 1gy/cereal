package io.github._1gy.cereal.parser;

import java.util.Objects;

public class ByteSlice {
    private final byte[] bytes;
    private final int offset;
    private final int length;

    private ByteSlice(byte[] bytes, int offset, int length) {
        this.bytes = bytes;
        this.offset = offset;
        this.length = length;
    }

    public byte[] toByteArray() {
        var result = new byte[length];
        System.arraycopy(bytes, offset, result, 0, length);
        return result;
    }

    public int length() {
        return length;
    }

    public byte get(int index) {
        if (index < 0 || bytes.length <= index + offset || length <= index) {
            throw new IndexOutOfBoundsException();
        }
        return bytes[offset + index];
    }

    public ByteSlice slice(Range range) {
        return of(this, range);
    }

    public boolean startsWith(byte[] prefix) {
        if (length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (bytes[offset + i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ByteSlice[" + offset + ".." + (offset + length) + ']';
    }

    public static ByteSlice of(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");
        return new ByteSlice(bytes, 0, bytes.length);
    }

    public static ByteSlice of(byte[] bytes, Range range) {
        Objects.requireNonNull(bytes, "bytes");
        Objects.requireNonNull(range, "range");

        final int offset;
        final int length;

        switch (range) {
            case Range.Of r -> {
                offset = r.start();
                length = r.end() - r.start();
            }
            case Range.To r -> {
                offset = 0;
                length = r.end();
            }
            case Range.From r -> {
                offset = r.start();
                length = bytes.length - r.start();
            }
            default -> {
                throw new IllegalArgumentException("Invalid range");
            }
        }

        final var invaliOffset = offset < 0 || bytes.length < offset;
        final var invalidLength = length < 0 || bytes.length < offset + length;

        if (invaliOffset || invalidLength) {
            throw new IllegalArgumentException("Invalid range");
        }

        return new ByteSlice(bytes, offset, length);
    }

    public static ByteSlice of(ByteSlice slice, Range range) {
        Objects.requireNonNull(slice, "slice");
        Objects.requireNonNull(range, "range");

        final int offset;
        final int length;

        switch (range) {
            case Range.Of r -> {
                offset = slice.offset + r.start();
                length = r.end() - r.start();
            }
            case Range.To r -> {
                offset = slice.offset;
                length = r.end();
            }
            case Range.From r -> {
                offset = slice.offset + r.start();
                length = slice.length - r.start();
            }
            default -> {
                throw new IllegalArgumentException("Invalid range");
            }
        }

        final var invaliOffset = offset < slice.offset || slice.offset + slice.length < offset;
        final var invalidLength = length < 0 || slice.offset + slice.length < offset + length;

        if (invaliOffset || invalidLength) {
            throw new IllegalArgumentException("Invalid range");
        }

        return new ByteSlice(slice.bytes, offset, length);
    }
}
