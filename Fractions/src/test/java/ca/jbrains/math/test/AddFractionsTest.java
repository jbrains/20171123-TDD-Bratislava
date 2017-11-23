package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Test;

public class AddFractionsTest {
    @Test
    public void zeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        Fraction sum = zero.plus(zero);
        Assert.assertEquals(0, sum.intValue());
    }

    @Test
    public void notZeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        final Fraction six = new Fraction(6);
        Fraction sum = six.plus(zero);
        Assert.assertEquals(6, sum.intValue());
    }

    @Test
    public void zeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(12));
        Assert.assertEquals(12, sum.intValue());
    }

    @Test
    public void notZeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(4).plus(new Fraction(13));
        Assert.assertEquals(17, sum.intValue());
    }

    @Test
    public void nonIntegersWithTheSameDenominator() throws Exception {
        final Fraction sum = new Fraction(1, 5).plus(new Fraction(2, 5));
        Assert.assertEquals(3, sum.getNumerator());
        Assert.assertEquals(5, sum.getDenominator());
    }

    @Test
    public void coprimeDenominators() throws Exception {
        final Fraction sum = new Fraction(3, 7).plus(new Fraction(2, 3));
        Assert.assertEquals(23, sum.getNumerator());
        Assert.assertEquals(21, sum.getDenominator());
    }

    public static class Fraction {
        private int numerator;
        private int denominator;

        public Fraction(final int intValue) {
            this(intValue, 1);
        }

        public Fraction(final int numerator, final int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        public Fraction plus(final Fraction that) {
            if (this.denominator == 1)
                return new Fraction(this.numerator + that.numerator);
            else if (this.denominator == that.denominator)
                return new Fraction(this.numerator + that.numerator, this.denominator);
            else
                return new Fraction(
                        this.numerator * that.denominator + that.numerator * this.denominator,
                        this.denominator * that.denominator);
        }

        public int intValue() {
            return numerator;
        }

        public int getNumerator() {
            return numerator;
        }

        public int getDenominator() {
            return denominator;
        }
    }
}
