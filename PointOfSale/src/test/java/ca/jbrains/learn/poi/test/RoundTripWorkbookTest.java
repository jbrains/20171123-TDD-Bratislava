package ca.jbrains.learn.poi.test;

import io.vavr.collection.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RoundTripWorkbookTest {
    @Test
    public void workbookStructure() throws Exception {
        final File simpleWorkbookFile = new File("./src/test/data/simple-workbook.xls");
        Assume.assumeTrue(simpleWorkbookFile.exists());

        final HSSFWorkbook simpleWorkbook = new HSSFWorkbook(new FileInputStream(simpleWorkbookFile));
        Assert.assertEquals(1, simpleWorkbook.getNumberOfSheets());

        final HSSFSheet sampleDataWorksheet = simpleWorkbook.getSheet("Sample Data");
        Assert.assertNotNull(sampleDataWorksheet);

        final HSSFRow headerRow = sampleDataWorksheet.getRow(0);
        Assert.assertNotNull(headerRow);

        final Iterator<Cell> cellsInFirstRow = headerRow.cellIterator();
        Assert.assertTrue(cellsInFirstRow.hasNext());

        final Cell cellA1 = cellsInFirstRow.next();
        Assert.assertEquals("strings", cellA1.getStringCellValue());

        final List<Cell> cellsInFirstRowAsList = List.ofAll(() -> headerRow.cellIterator());
        Assert.assertEquals(3, cellsInFirstRowAsList.size());
        Assert.assertEquals(
                List.of("strings", "money amounts", "plain numbers"),
                cellsInFirstRowAsList.map(Cell::getStringCellValue));
    }
}
