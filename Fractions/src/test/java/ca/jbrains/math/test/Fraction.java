package ca.jbrains.math.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fraction {
    private int numerator;
    private int denominator;

    public Fraction(final int intValue) {
        this(intValue, 1);
    }

    public Fraction(final int numerator, final int denominator) {
        if (denominator == 0)
            throw new IllegalArgumentException("A Fraction cannot have a zero denominator.");

        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Fraction parse(final String text) {
        try {
            if (text.contains("/")) {
                return parseAsFraction(text);
            } else {
                return parseAsInteger(text);
            }
        } catch (NumberFormatException | IllegalStateException handled) {
            throw new IllegalArgumentException(
                    String.format("I don't know how to parse a Fraction from \"%s\"", text));
        }
    }

    private static Fraction parseAsInteger(final String text) {
        return new Fraction(Integer.parseInt(text, 10));
    }

    private static Fraction parseAsFraction(final String text) {
        final Matcher matcher = Pattern.compile("(?<numerator>([\\-]?)\\d+)/(?<denominator>([\\-]?)\\d+)").matcher(text);
        // SMELL Programming by accident. Thanks, Java.
        matcher.matches();
        return new Fraction(
                Integer.parseInt(matcher.group("numerator"), 10),
                Integer.parseInt(matcher.group("denominator"), 10));
    }

    public Fraction plus(final Fraction that) {
        return new Fraction(
                this.numerator * that.denominator + that.numerator * this.denominator,
                this.denominator * that.denominator);
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof Fraction) {
            Fraction that = (Fraction) other;
            return this.numerator * that.denominator == this.denominator * that.numerator;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return numerator * 137 + denominator * 67;
    }

    @Override
    public String toString() {
        return String.format("%d/%d", numerator, denominator);
    }
}
