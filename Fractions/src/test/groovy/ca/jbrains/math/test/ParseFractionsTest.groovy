package ca.jbrains.math.test

import spock.lang.Specification
import spock.lang.Unroll

class ParseFractionsTest extends Specification {
    public static class Fraction {
        private final int intValue;
        
        public Fraction(int intValue) {
            this.intValue = intValue;
        }

        public static Fraction parse(String text) {
            if ("0".equals(text))
            return new Fraction(0);
            else
                return new Fraction(1);
        }

        public int intValue() {
            return intValue;
        }
    }

    @Unroll
    def "integer #intValue"() {
        expect:
        Fraction.parse(text).intValue() == intValue

        where:
        text | intValue
        "0" | 0
        "1" | 1
    }
}