package io.github._1gy.cereal.parser;

public final class Primitive {

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
