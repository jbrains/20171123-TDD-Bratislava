package ca.jbrains.math.test

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll


class AddFractionsTest extends Specification {
    @Ignore("First, parse the fraction")
    @Unroll()
    def "#addend + #augend = #sum"() {
        expect:
        Fraction.parse(addend).plus(Fraction.parse(augend)) == Fraction.parse(sum)

        where:
        addend | augend | sum
        "0" | "0" | "0"
    }
}