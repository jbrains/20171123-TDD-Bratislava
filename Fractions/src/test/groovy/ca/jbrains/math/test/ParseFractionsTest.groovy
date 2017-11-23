package ca.jbrains.math.test

import spock.lang.Specification
import spock.lang.Unroll

class ParseFractionsTest extends Specification {
    public static class Fraction {
        public static Fraction parse(String text) {
            return new Fraction();
        }

        public int intValue() {
            return 0;
        }
    }

    @Unroll
    def "integer #intValue"() {
        expect:
        Fraction.parse(text).intValue() == intValue

        where:
        text | intValue
        "0" | 0
    }
}