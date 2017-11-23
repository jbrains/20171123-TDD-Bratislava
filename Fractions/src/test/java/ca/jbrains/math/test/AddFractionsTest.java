package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Test;

public class AddFractionsTest {
    @Test
    public void zeroPlusZero() throws Exception {
        final Fraction zero = new Fraction();
        Fraction sum = zero.plus(zero);
        Assert.assertEquals(0, sum.intValue());
    }

    public static class Fraction {
        public Fraction plus(final Fraction zero) {
            return this;
        }

        public int intValue() {
            return 0;
        }
    }
}
