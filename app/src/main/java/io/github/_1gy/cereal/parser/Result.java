package io.github._1gy.cereal.parser;

import java.util.Objects;
import java.util.function.Function;

public sealed interface Result<T, E> permits Result.Ok, Result.Err {

    boolean isOk();

    boolean isErr();

    T unwrap();

    E unwrapErr();

    public default <U> Result<U, E> map(Function<T, U> op) {
        switch (this) {
            case Ok<T, E> ok -> {
                return ok(op.apply(ok.value));
            }
            case Err<T, E> err -> {
                return err(err.error);
            }
        }
    }

    public default <U> Result<U, E> andThen(Function<T, Result<U, E>> op) {
        switch (this) {
            case Ok<T, E> ok -> {
                return op.apply(ok.value);
            }
            case Err<T, E> err -> {
                return err(err.error);
            }
        }
    }

    public static <T, E> Result<T, E> ok(T value) {
        return new Ok<>(value);
    }

    public static <T, E> Result<T, E> err(E error) {
        return new Err<>(error);
    }

    public final class Ok<T, E> implements Result<T, E> {

        private final T value;

        public Ok(T value) {
            this.value = value;
        }

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

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final Ok<?, ?> other = (Ok<?, ?>) obj;
            return Objects.equals(value, other.value);
        }

        @Override
        public String toString() {
            return "Ok [value=" + value + "]";
        }
    }

    public final class Err<T, E> implements Result<T, E> {

        private final E error;

        public Err(E error) {
            this.error = error;
        }

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

        @Override
        public int hashCode() {
            return Objects.hash(error);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final Err<?, ?> other = (Err<?, ?>) obj;
            return Objects.equals(error, other.error);
        }

        @Override
        public String toString() {
            return "Err [error=" + error + "]";
        }
    }
}
