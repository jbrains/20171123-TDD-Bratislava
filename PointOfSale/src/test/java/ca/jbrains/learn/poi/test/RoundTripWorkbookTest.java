package ca.jbrains.learn.poi.test;

import io.vavr.collection.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;

public class RoundTripWorkbookTest {

    private File simpleWorkbookFile;

    @Before
    public void workbook() throws Exception {
        simpleWorkbookFile = new File("./src/test/data/simple-workbook.xls");
        Assume.assumeTrue(simpleWorkbookFile.exists());
    }

    @Test
    public void workbookStructure() throws Exception {
        final HSSFWorkbook simpleWorkbook = new HSSFWorkbook(new FileInputStream(simpleWorkbookFile));
        Assert.assertEquals(1, simpleWorkbook.getNumberOfSheets());

        final HSSFSheet sampleDataWorksheet = simpleWorkbook.getSheet("Sample Data");
        Assert.assertNotNull(sampleDataWorksheet);

        final HSSFRow headerRow = sampleDataWorksheet.getRow(0);
        Assert.assertNotNull(headerRow);

        final Iterator<Cell> cellsInFirstRow = headerRow.cellIterator();
        Assert.assertTrue(cellsInFirstRow.hasNext());
    }

    @Test
    public void readHeaderRowCellValuesAsText() throws Exception {
        final HSSFRow headerRow = sanityCheckForWorkbook();

        final List<Cell> cellsInFirstRowAsList = List.ofAll(() -> headerRow.cellIterator());
        final List<String> cellValuesInFirstRowAsText = cellsInFirstRowAsList.map(Cell::getStringCellValue);

        Assert.assertEquals(
                List.of("strings", "money amounts", "plain numbers"),
                cellValuesInFirstRowAsText);
    }

    private HSSFRow sanityCheckForWorkbook() throws IOException {
        final HSSFWorkbook simpleWorkbook = new HSSFWorkbook(new FileInputStream(simpleWorkbookFile));
        Assume.assumeThat(simpleWorkbook.getNumberOfSheets(), is(1));

        final HSSFSheet sampleDataWorksheet = simpleWorkbook.getSheet("Sample Data");
        Assume.assumeNotNull(sampleDataWorksheet);

        final HSSFRow headerRow = sampleDataWorksheet.getRow(0);
        Assume.assumeNotNull(headerRow);
        return headerRow;
    }
}
