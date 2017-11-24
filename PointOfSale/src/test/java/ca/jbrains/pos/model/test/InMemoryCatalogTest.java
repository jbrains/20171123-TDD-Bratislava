package ca.jbrains.pos.model.test;

import ca.jbrains.pos.controller.test.Catalog;
import ca.jbrains.pos.controller.test.Price;
import io.vavr.collection.HashMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class InMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.cents(1250);

        Assert.assertEquals(
                matchingPrice,
                catalogWith("::any barcode::", matchingPrice)
                        .findPrice("::any barcode::"));
    }

    private Catalog catalogWith(final String barcode, final Price matchingPrice) {
        return new InMemoryCatalog(
                    HashMap.of(barcode, matchingPrice).toJavaMap());
    }

    @Test
    public void productNotFound() throws Exception {
        Assert.assertEquals(
                null,
                catalogWithout("::any missing barcode::")
                        .findPrice("::any missing barcode::"));
    }

    private Catalog catalogWithout(final String barcodeToAvoid) {
        return new InMemoryCatalog(
                    HashMap.<String, Price>empty().toJavaMap());
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(final Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(final String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
