package ca.jbrains.pos.model.test;

import ca.jbrains.pos.controller.test.Catalog;
import ca.jbrains.pos.controller.test.Price;
import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.cents(1250);

        Assert.assertEquals(
                matchingPrice,
                catalogWith("::any barcode::", matchingPrice)
                        .findPrice("::any barcode::"));
    }

    protected abstract Catalog catalogWith(String barcode, Price matchingPrice);

    @Test
    public void productNotFound() throws Exception {
        Assert.assertEquals(
                null,
                catalogWithout("::any missing barcode::")
                        .findPrice("::any missing barcode::"));
    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
