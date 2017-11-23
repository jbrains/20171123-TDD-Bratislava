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
        final Sale sale = new Sale(new Catalog(new HashMap<String, String>() {{
            put("1234", "€ 5.50");
        }}), display);

        sale.onBarcode("1234");

        Assert.assertEquals("€ 5.50", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new Catalog(new HashMap<String, String>() {{
            put("2345", "€ 1.87");
        }}), display);

        sale.onBarcode("2345");

        Assert.assertEquals("€ 1.87", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new Catalog(new HashMap<String, String>() {{
            put("1234", "€ 5.50");
            put("2345", "€ 1.87");
        }}), display);

        sale.onBarcode("000");

        Assert.assertEquals("Product not found for 000", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new Catalog(Collections.emptyMap()), display);

        sale.onBarcode("");

        Assert.assertEquals("Scanning error: empty barcode", display.getText());
    }

    public static class Sale {
        private final Catalog catalog;
        private final Display display;

        public Sale(final Catalog catalog, final Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(final String barcode) {
            if ("".equals(barcode))
                display.displayEmptyBarcodeMessage();
            else if (catalog.hasBarcode(barcode))
                display.displayPrice(catalog.findPrice(barcode));
            else
                display.displayProductNotFoundMessage(barcode);
        }
    }

    public static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void displayPrice(final String price) {
            this.text = price;
        }

        public void displayProductNotFoundMessage(final String barcode) {
            this.text = String.format("Product not found for %s", barcode);
        }

        public void displayEmptyBarcodeMessage() {
            this.text = "Scanning error: empty barcode";
        }
    }

    public static class Catalog {
        private final Map<String, String> pricesByBarcode;

        public Catalog(final Map<String, String> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public boolean hasBarcode(final String barcode) {
            return pricesByBarcode.containsKey(barcode);
        }

        public String findPrice(final String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
