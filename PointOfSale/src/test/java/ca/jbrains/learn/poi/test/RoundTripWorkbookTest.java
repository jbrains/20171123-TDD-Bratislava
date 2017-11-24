package ca.jbrains.learn.poi.test;

import io.vavr.collection.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL;
import static org.hamcrest.Matchers.is;

public class RoundTripWorkbookTest {

    public static final double CLOSE_ENOUGH = 0.000001d;
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
        final HSSFRow headerRow = headerRow();

        final List<Cell> cellsInFirstRowAsList = List.ofAll(() -> headerRow.cellIterator());
        final List<String> cellValuesInFirstRowAsText = cellsInFirstRowAsList.map(Cell::getStringCellValue);

        Assert.assertEquals(
                List.of("strings", "money amounts", "plain numbers"),
                cellValuesInFirstRowAsText);
    }

    @Test
    public void readCellValuesAsPlainNumbers() throws Exception {
        final HSSFSheet sampleDataWorksheet = sampleDataWorksheet();
        final List<Cell> plainNumbersColumn = columnOf(2, bodyRowsOf(rowsOf(sampleDataWorksheet)));
        Assert.assertEquals(
                List.of(12.0d, 35.0d, 46.0d, 57.0d),
                plainNumbersColumn.map(Cell::getNumericCellValue));
    }

    @Test
    public void readCellValueAsCurrency() throws Exception {
        final Cell firstMoneyAmountCell = firstBodyCellOf(columnOf(1, sampleDataWorksheet()));
        Assert.assertEquals(7.95d, firstMoneyAmountCell.getNumericCellValue(), CLOSE_ENOUGH);

        // The numbers are truly magic. HSSFDataFormat doesn't explain them.
        final int magicNumberForSlovakEuroCurrencyFormat = 166;
        Assert.assertEquals(
                magicNumberForSlovakEuroCurrencyFormat,
                firstMoneyAmountCell.getCellStyle().getDataFormat());
    }

    private List<Cell> columnOf(final int columnIndex, final HSSFSheet worksheet) throws IOException {
        return columnOf(columnIndex, rowsOf(worksheet));
    }

    private Cell firstBodyCellOf(final List<Cell> column) {
        return bodyRowsOf(column).take(1).get();
    }

    private List<Cell> columnOf(final int columnIndex, final List<Row> bodyRows) {
        return bodyRows.map(each -> each.getCell(columnIndex, RETURN_BLANK_AS_NULL));
    }

    private List<Row> rowsOf(final HSSFSheet sampleDataWorksheet) {
        return List.ofAll(() -> sampleDataWorksheet.iterator());
    }

    private <T> List<T> bodyRowsOf(final List<T> table) {
        return table.drop(1);
    }

    private HSSFRow headerRow() throws IOException {
        final HSSFSheet sampleDataWorksheet = sampleDataWorksheet();

        final HSSFRow headerRow = sampleDataWorksheet.getRow(0);
        Assume.assumeNotNull(headerRow);
        return headerRow;
    }

    private HSSFSheet sampleDataWorksheet() throws IOException {
        final HSSFWorkbook simpleWorkbook = new HSSFWorkbook(new FileInputStream(simpleWorkbookFile));
        Assume.assumeThat(simpleWorkbook.getNumberOfSheets(), is(1));

        final HSSFSheet sampleDataWorksheet = simpleWorkbook.getSheet("Sample Data");
        Assume.assumeNotNull(sampleDataWorksheet);
        return sampleDataWorksheet;
    }
}
