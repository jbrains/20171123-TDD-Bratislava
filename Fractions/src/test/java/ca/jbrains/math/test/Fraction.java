package ca.jbrains.math.test;

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
                final String[] parts = text.split("\\/");
                return new Fraction(Integer.parseInt(parts[0], 10), Integer.parseInt(parts[1], 10));
            } else
                return new Fraction(Integer.parseInt(text, 10));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException handled) {
            throw new IllegalArgumentException(
                    String.format("I don't know how to parse a Fraction from \"%s\"", text));
        }
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
