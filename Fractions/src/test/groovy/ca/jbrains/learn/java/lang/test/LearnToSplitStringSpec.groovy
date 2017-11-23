package ca.jbrains.learn.java.lang.test

import spock.lang.Specification


class LearnToSplitStringSpec extends Specification {
    def "string does not contain delimiter"() {
        expect:
        "15".split("a") == ["15"]
    }

    def "string contains delimiter, but there are empty 'parts'"() {
        // Well, this sucks.
        // I would prefer that these two cases had the same result, but
        // I understand why they differ.
        expect:
        "15a".split("a") == ["15"]
        "a15".split("a") == ["", "15"]
    }
}