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
                HashMap.of("12345", matchingPrice).toJavaMap());

        Assert.assertEquals(matchingPrice, catalog.findPrice("12345"));
    }

    @Test
    public void productNotFound() throws Exception {
        final InMemoryCatalog catalog = new InMemoryCatalog(
                HashMap.<String, Price>empty().toJavaMap());
        
        Assert.assertEquals(null, catalog.findPrice("99999"));
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
