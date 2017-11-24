package ca.jbrains.pos.model.test;

import ca.jbrains.pos.controller.test.Price;
import io.vavr.collection.HashMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class InMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.cents(1250);
        final InMemoryCatalog catalog = new InMemoryCatalog(
                HashMap.of("::any barcode::", matchingPrice).toJavaMap());

        Assert.assertEquals(matchingPrice, catalog.findPrice("::any barcode::"));
    }

    @Test
    public void productNotFound() throws Exception {
        final InMemoryCatalog catalog = new InMemoryCatalog(
                HashMap.<String, Price>empty().toJavaMap());

        Assert.assertEquals(null, catalog.findPrice("::any missing barcode::"));
    }

    public static class InMemoryCatalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(final Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(final String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
