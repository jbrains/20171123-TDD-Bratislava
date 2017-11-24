package ca.jbrains.pos.model.test;

import ca.jbrains.pos.controller.test.Price;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class LoadCatalogFromHssfWorkbookTest {
    @Test
    @Ignore("I'm figuring out how to parse cells as barcode and price first.")
    public void happyPath() throws Exception {
        final FileInputStream workbookFile = new FileInputStream(new File("./src/test/data/excel-catalog/happy-path-catalog.xls"));
        Assert.assertEquals(
                HashMap.of(
                        "12345", Price.cents(795),
                        "23456", Price.cents(1250)
                        , "34567", Price.cents(82374637),
                        "45678", Price.cents(98)),
                loadCatalogFromHssfWorksheet(new HSSFWorkbook(workbookFile).getSheet("Products")));
    }

    private Map<String, Price> loadCatalogFromHssfWorksheet(final HSSFSheet productsSheet) {
        return HashMap.empty();
    }
}
