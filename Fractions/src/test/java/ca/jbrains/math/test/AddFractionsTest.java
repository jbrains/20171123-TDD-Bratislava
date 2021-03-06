package ca.jbrains.math.test;

import org.junit.Assert;
import org.junit.Test;

public class AddFractionsTest {
    @Test
    public void zeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        Fraction sum = zero.plus(zero);
        Assert.assertEquals(new Fraction(0), sum);
    }

    @Test
    public void notZeroPlusZero() throws Exception {
        final Fraction zero = new Fraction(0);
        final Fraction six = new Fraction(6);
        Fraction sum = six.plus(zero);
        Assert.assertEquals(new Fraction(6), sum);
    }

    @Test
    public void zeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(12));
        Assert.assertEquals(new Fraction(12), sum);
    }

    @Test
    public void notZeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(4).plus(new Fraction(13));
        Assert.assertEquals(new Fraction(17), sum);
    }

    @Test
    public void nonIntegersWithTheSameDenominator() throws Exception {
        final Fraction sum = new Fraction(1, 5).plus(new Fraction(2, 5));
        Assert.assertEquals(new Fraction(3, 5), sum);
    }

    @Test
    public void coprimeDenominators() throws Exception {
        final Fraction sum = new Fraction(3, 7).plus(new Fraction(2, 3));
        Assert.assertEquals(new Fraction(23, 21), sum);
    }

    @Test
    public void denominatorsWithACommonFactor() throws Exception {
        Assert.assertEquals(new Fraction(3, 4), new Fraction(1, 2).plus(new Fraction(1, 4)));
    }

    @Test
    public void zeroDenominator() throws Exception {
        try {
            new Fraction(6, 0);
            Assert.fail("How did you create a Fraction with a 0 denominator?!");
        }
        catch (IllegalArgumentException expected) {}
    }

}
