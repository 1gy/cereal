package io.github._1gy.cereal.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RangeTest {
    @Test
    void itWorks() {
        var range = Range.of(1, 2);
        var text = switch (range) {
            case Range.Of of -> "of";
            case Range.To to -> "to";
            case Range.From from -> "from";
            default -> "default";
        };
        assertEquals("of", text);
    }

    @Test
    void testOf() {
        var of = Range.of(1, 2);
        assertEquals(of, new Range.Of(1, 2));
        assertEquals(of.toString(), "Of[start=1, end=2]");
    }

    @Test
    void testFrom() {
        var from = Range.from(1);
        assertEquals(from, new Range.From(1));
        assertEquals(from.toString(), "From[start=1]");
    }

    @Test
    void testTo() {
        var to = Range.to(2);
        assertEquals(to, new Range.To(2));
        assertEquals(to.toString(), "To[end=2]");
    }
}
