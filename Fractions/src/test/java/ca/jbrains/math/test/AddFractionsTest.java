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

    public static class Fraction {
        private final int intValue;

        public Fraction(final int intValue) {
            this.intValue = intValue;
        }

        public Fraction plus(final Fraction that) {
            if (this.intValue != 0 && that.intValue != 0)
                return new Fraction(this.intValue + that.intValue);
            else if (that.intValue == 0)
                return this;
            else
                return that;
        }

        public int intValue() {
            return intValue;
        }
    }
}
