package ca.jbrains.math.test

import spock.lang.Specification
import spock.lang.Unroll


class AddFractionsSpec extends Specification {
    @Unroll
    def "#augend + #addend = #sum"() {
        expect:
        Fraction.parse(augend).plus(Fraction.parse(addend)) == Fraction.parse(sum)

        where:
        augend | addend | sum
        "0" | "0" | "0"
    }

}