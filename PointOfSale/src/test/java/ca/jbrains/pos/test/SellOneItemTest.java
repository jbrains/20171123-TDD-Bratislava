package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new HashMap<String, String>() {{
            put("1234", "€ 5.50");
        }}, display);

        sale.onBarcode("1234");

        Assert.assertEquals("€ 5.50", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new HashMap<String, String>() {{
            put("2345", "€ 1.87");
        }}, display);

        sale.onBarcode("2345");

        Assert.assertEquals("€ 1.87", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new HashMap<String, String>() {{
            put("1234", "€ 5.50");
            put("2345", "€ 1.87");
        }}, display);

        sale.onBarcode("000");

        Assert.assertEquals("Product not found for 000", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(Collections.emptyMap(), display);

        sale.onBarcode("");

        Assert.assertEquals("Scanning error: empty barcode", display.getText());
    }

    public static class Sale {
        private final Map<String, String> pricesByBarcode;
        private final Display display;

        public Sale(final Map<String, String> pricesByBarcode, final Display display) {
            this.display = display;
            this.pricesByBarcode = pricesByBarcode;
        }

        public void onBarcode(final String barcode) {
            if ("".equals(barcode))
                display.setText("Scanning error: empty barcode");
            else if (pricesByBarcode.containsKey(barcode))
                display.setText(pricesByBarcode.get(barcode));
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
