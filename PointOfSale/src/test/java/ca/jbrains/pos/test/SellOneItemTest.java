package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("1234");

        Assert.assertEquals("€ 5.50", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("2345");

        Assert.assertEquals("€ 1.87", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("000");

        Assert.assertEquals("Product not found for 000", display.getText());
    }

    public static class Sale {
        private final Display display;

        public Sale(final Display display) {
            this.display = display;
        }

        public void onBarcode(final String barcode) {
            if ("1234".equals(barcode))
                display.setText("€ 5.50");
            else if ("2345".equals(barcode))
                display.setText("€ 1.87");
            else
                display.setText(String.format("Product not found for %s", barcode));
        }
    }

    public static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(final String text) {
            this.text = text;
        }
    }
}
