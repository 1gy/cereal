package io.github._1gy.cereal.parser;

public final class Primitive {

    public static final Parser<ByteSlice, Byte, String> beI8() {
        return input -> {
            if (input.length() < 1) {
                return ParseResult.err("input too short");
            }
            var value = input.get(0);
            var rest = input.slice(Range.from(1));
            return ParseResult.ok(rest, value);
        };
    }

    public static final Parser<ByteSlice, Short, String> beI16() {
        return input -> {
            if (input.length() < 2) {
                return ParseResult.err("input too short");
            }
            var value = (short) ((input.get(0) << 8) | input.get(1));
            var rest = input.slice(Range.from(2));
            return ParseResult.ok(rest, value);
        };
    }

    public static final Parser<ByteSlice, Integer, String> beI32() {
        return input -> {
            if (input.length() < 4) {
                return ParseResult.err("input too short");
            }
            var value = (input.get(0) << 24) | (input.get(1) << 16) | (input.get(2) << 8) | input.get(3);
            var rest = input.slice(Range.from(4));
            return ParseResult.ok(rest, value);
        };
    }

    public static final Parser<ByteSlice, Long, String> beI64() {
        return input -> {
            if (input.length() < 8) {
                return ParseResult.err("input too short");
            }
            var value = ((long) input.get(0) << 56) | ((long) input.get(1) << 48) | ((long) input.get(2) << 40)
                    | ((long) input.get(3) << 32) | ((long) input.get(4) << 24) | ((long) input.get(5) << 16)
                    | ((long) input.get(6) << 8) | (long) input.get(7);
            var rest = input.slice(Range.from(8));
            return ParseResult.ok(rest, value);
        };
    }

    public static final Parser<ByteSlice, ByteSlice, String> tag(byte[] bytes) {
        return input -> {
            if (input.startsWith(bytes)) {
                var rest = input.slice(Range.from(bytes.length));
                var value = ByteSlice.of(bytes);
                return ParseResult.ok(rest, value);
            } else {
                return ParseResult.err("tag not found");
            }
        };
    }

}
