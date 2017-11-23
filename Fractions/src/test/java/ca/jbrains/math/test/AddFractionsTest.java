package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Ignore;
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

    public static class Fraction {
        private final int intValue;

        public Fraction(final int intValue) {
            this.intValue = intValue;
        }

        public Fraction plus(final Fraction zero) {
            return this;
        }

        public int intValue() {
            return intValue;
        }
    }
}
