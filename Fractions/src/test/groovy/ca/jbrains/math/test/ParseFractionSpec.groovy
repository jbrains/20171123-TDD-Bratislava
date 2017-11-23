package ca.jbrains.math.test

import spock.lang.Specification
import spock.lang.Unroll


class ParseFractionSpec extends Specification {
    @Unroll
    def "#text parses as #fraction"() {
        expect:
        Fraction.parse(text) == fraction

        where:
        text | fraction
        "0" | new Fraction(0)
        "5" | new Fraction(5)
    }

}