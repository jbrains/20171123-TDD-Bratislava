package ca.jbrains.pos.controller.test;

public class Price {
    public static Price cents(final int centsValue) {
        return new Price();
    }

    @Override
    public String toString() {
        return "a Price";
    }
}
