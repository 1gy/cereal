package io.github._1gy.cereal.parser;

@FunctionalInterface
public interface Parser<I, O, E> {
    Result<I, O, E> parse(I input);
}
