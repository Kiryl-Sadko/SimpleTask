package com.jazzteam.task;

import com.jazzteam.task.impl.LongConverterImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class LongConverterTest {

    private final LongConverter converter = new LongConverterImpl();

    private static Stream<Arguments> answers() {
        return Stream.of(
                arguments(1_234_289L, "один миллион двести тридцать четыре тысячи двести восемьдесят девять"),
                arguments(1_229_800_340_056L, "один триллион двести двадцать девять миллиардов восемьсот  миллионов триста сорок  тысяч пятьдесят шесть")
        );
    }

    @ParameterizedTest
    @MethodSource("answers")
    void shouldEquals(long number, String response) {
        Assertions.assertEquals(response, converter.toString(number).trim());
    }
}
