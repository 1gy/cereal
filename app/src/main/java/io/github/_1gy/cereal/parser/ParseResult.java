package io.github._1gy.cereal.parser;

public sealed interface ParseResult<I, O, E> permits ParseResult.Ok, ParseResult.Err {

    boolean isOk();

    boolean isErr();

    I rest();

    O value();

    E error();

    public static <I, O, E> ParseResult<I, O, E> ok(I rest, O value) {
        return new Ok<>(rest, value);
    }

    public static <I, O, E> ParseResult<I, O, E> err(E error) {
        return new Err<>(error);
    }

    record Ok<I, O, E>(I rest, O value) implements ParseResult<I, O, E> {
        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public boolean isErr() {
            return false;
        }

        @Override
        public E error() {
            throw new IllegalStateException("not an error");
        }
    }

    record Err<I, O, E>(E error) implements ParseResult<I, O, E> {
        @Override
        public boolean isOk() {
            return false;
        }

        @Override
        public boolean isErr() {
            return true;
        }

        @Override
        public I rest() {
            throw new IllegalStateException("not a rest");
        }

        @Override
        public O value() {
            throw new IllegalStateException("not a value");
        }
    }
}
