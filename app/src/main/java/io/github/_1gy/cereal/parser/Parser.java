package io.github._1gy.cereal.parser;

@FunctionalInterface
public interface Parser<I, O, E> {
    Result<ParsedValue<I, O>, E> parse(I input);
}
