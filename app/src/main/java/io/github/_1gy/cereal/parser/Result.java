package io.github._1gy.cereal.parser;

import java.util.function.Function;

public sealed interface Result<T, E> permits Result.Ok, Result.Err {

    boolean isOk();

    boolean isErr();

    T unwrap();

    E unwrapErr();

    public default <U> Result<U, E> map(Function<T, U> op) {
        switch (this) {
            case Ok<T, E> ok -> {
                return ok(op.apply(ok.value()));
            }
            case Err<T, E> err -> {
                return err(err.error());
            }
        }
    }

    public default <U> Result<U, E> andThen(Function<T, Result<U, E>> op) {
        switch (this) {
            case Ok<T, E> ok -> {
                return op.apply(ok.value());
            }
            case Err<T, E> err -> {
                return err(err.error());
            }
        }
    }

    public static <T, E> Result<T, E> ok(T value) {
        return new Ok<>(value);
    }

    public static <T, E> Result<T, E> err(E error) {
        return new Err<>(error);
    }

    record Ok<T, E>(T value) implements Result<T, E> {
        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public boolean isErr() {
            return false;
        }

        @Override
        public T unwrap() {
            return value;
        }

        @Override
        public E unwrapErr() {
            throw new IllegalStateException("not an error");
        }
    }

    record Err<T, E>(E error) implements Result<T, E> {
        @Override
        public boolean isOk() {
            return false;
        }

        @Override
        public boolean isErr() {
            return true;
        }

        @Override
        public T unwrap() {
            throw new IllegalStateException("not a value");
        }

        @Override
        public E unwrapErr() {
            return error;
        }
    }
}
