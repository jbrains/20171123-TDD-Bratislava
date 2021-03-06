package ca.jbrains.math.test

import spock.lang.Specification
import spock.lang.Unroll


class ParseFractionSpec extends Specification {
    @Unroll
    def "#text parses as #fraction"() {
        expect:
        Fraction.parse(text) == fraction

        where:
        text    | fraction
        "0"     | new Fraction(0)
        "5"     | new Fraction(5)
        "-2"    | new Fraction(-2)
        "1/2"   | new Fraction(1, 2)
        "15/25" | new Fraction(3, 5)
        "5/-3"  | new Fraction(-5, 3)
    }

    @Unroll
    def "\"#text\" does not parse as a fraction"() {
        when:
        Fraction.parse(text)

        then:
        thrown(IllegalArgumentException)

        where:
        text << ["", " ", "\t", "/", "1/", "/8", "a/b", "a/6", "7/b", "abc"]
    }

    @Unroll
    def "\"#text\" parses as an invalid fraction"() {
        when:
        Fraction.parse(text)

        then:
        thrown(IllegalArgumentException)

        where:
        text << ["1/0", "-345/0", "91237/0", "0/0"]
    }

}