package io.github._1gy.cereal.parser;

public sealed interface Range {
    public static Range of(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("Invalid range");
        }
        return new Of(start, end);
    }

    public static Range to(int end) {
        return new To(end);
    }

    public static Range from(int start) {
        return new From(start);
    }

    final record Of(int start, int end) implements Range {
    }

    final record To(int end) implements Range {
    }

    final record From(int start) implements Range {
    }
}
