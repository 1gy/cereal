package io.github._1gy.cereal.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github._1gy.cereal.parser.Result.Err;
import io.github._1gy.cereal.parser.Result.Ok;

public final class Combinator {

    public static <I, O, E, U> Parser<I, U, E> map(Parser<I, O, E> parser, Function<O, U> mapper) {
        return input -> {
            switch (parser.parse(input)) {
                case Ok<ParsedValue<I, O>, E> ok -> {
                    var parsed = ok.value();
                    return Result.ok(ParsedValue.of(parsed.rest(), mapper.apply(parsed.value())));
                }
                case Err<?, E> err -> {
                    return Result.err(err.error());
                }
            }
        };
    }

    public static <I, O, E> Parser<I, List<O>, E> iterator(Parser<I, O, E> parser) {
        return input -> {
            var result = new ArrayList<O>();
            var rest = input;
            while (true) {
                switch (parser.parse(rest)) {
                    case Ok<ParsedValue<I, O>, E> ok -> {
                        var parsed = ok.value();
                        result.add(parsed.value());
                        rest = parsed.rest();
                    }
                    case Err<?, E> err -> {
                        return Result.ok(ParsedValue.of(rest, result));
                    }
                }
            }
        };
    }

}
