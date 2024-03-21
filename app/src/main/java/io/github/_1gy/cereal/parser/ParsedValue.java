package io.github._1gy.cereal.parser;

public record ParsedValue<I, O>(I rest, O value) {
    public static <I, O> ParsedValue<I, O> of(I rest, O value) {
        return new ParsedValue<>(rest, value);
    }
}
