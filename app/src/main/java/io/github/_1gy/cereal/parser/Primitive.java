package io.github._1gy.cereal.parser;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

public final class Primitive {

    private static final VarHandle BE_SHORT_HANDLE = MethodHandles.byteArrayViewVarHandle(short[].class,
            ByteOrder.BIG_ENDIAN);
    private static final VarHandle BE_INT_HANDLE = MethodHandles.byteArrayViewVarHandle(int[].class,
            ByteOrder.BIG_ENDIAN);
    private static final VarHandle BE_LONG_HANDLE = MethodHandles.byteArrayViewVarHandle(long[].class,
            ByteOrder.BIG_ENDIAN);

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
            var buffer = input.slice(Range.to(2)).toByteArray();
            var value = (short) BE_SHORT_HANDLE.get(buffer, 0);
            var rest = input.slice(Range.from(2));
            return ParseResult.ok(rest, value);
        };
    }

    public static final Parser<ByteSlice, Integer, String> beI32() {
        return input -> {
            if (input.length() < 4) {
                return ParseResult.err("input too short");
            }
            var buffer = input.slice(Range.to(4)).toByteArray();
            var value = (int) BE_INT_HANDLE.get(buffer, 0);
            var rest = input.slice(Range.from(4));
            return ParseResult.ok(rest, value);
        };
    }

    public static final Parser<ByteSlice, Long, String> beI64() {
        return input -> {
            if (input.length() < 8) {
                return ParseResult.err("input too short");
            }
            var buffer = input.slice(Range.to(8)).toByteArray();
            var value = (long) BE_LONG_HANDLE.get(buffer, 0);
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
