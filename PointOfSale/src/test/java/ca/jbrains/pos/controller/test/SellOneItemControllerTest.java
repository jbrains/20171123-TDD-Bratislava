package ca.jbrains.pos.controller.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class SellOneItemControllerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.cents(795);

        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("12345"));
            will(returnValue(matchingPrice));

            oneOf(display).displayPrice(with(matchingPrice));
        }});

        new SellOneItemController(catalog, display).onBarcode("12345");
    }

    @Test
    public void productNotFound() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("12345"));
            will(returnValue(null));

            oneOf(display).displayProductNotFoundMessage(with("12345"));
        }});

        new SellOneItemController(catalog, display).onBarcode("12345");
    }

    public interface Catalog {
        Price findPrice(String barcode);
    }

    public interface Display {
        void displayPrice(Price price);

        void displayProductNotFoundMessage(String barcodeNotFound);
    }

    public static class Price {
        public static Price cents(final int centsValue) {
            return new Price();
        }

        @Override
        public String toString() {
            return "a Price";
        }
    }

    public static class SellOneItemController {
        private final Catalog catalog;
        private final Display display;

        public SellOneItemController(final Catalog catalog, final Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(final String barcode) {
            final Price price = catalog.findPrice(barcode);
            if (price == null)
                display.displayProductNotFoundMessage(barcode);
            else
                display.displayPrice(price);
        }
    }
}
