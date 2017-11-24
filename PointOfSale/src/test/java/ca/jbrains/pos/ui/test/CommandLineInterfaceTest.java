package ca.jbrains.pos.ui.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class CommandLineInterfaceTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private final BarcodeScannedListener barcodeScannedListener = context.mock(BarcodeScannedListener.class);

    @Test
    public void noCommands() throws Exception {
        context.checking(new Expectations() {{
            never(barcodeScannedListener);
        }});

        process(new StringReader(""));
    }

    @Test
    public void oneCommand() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode::");
        }});

        process(new StringReader("::barcode::"));
    }

    private void process(final Reader commandSource) throws IOException {
        final String theLine = new BufferedReader(commandSource).readLine();
        if (theLine != null)
            barcodeScannedListener.onBarcode(theLine);
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
