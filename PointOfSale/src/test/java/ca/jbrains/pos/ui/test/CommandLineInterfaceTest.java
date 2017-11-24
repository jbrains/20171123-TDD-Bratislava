package ca.jbrains.pos.ui.test;

import io.vavr.collection.List;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Ignore;
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

    @Test
    @Ignore
    public void severalCommands() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode 1::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 2::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 3::");
        }});

        process(new StringReader("::barcode 1::\n::barcode 2::\n::barcode 3::"));
    }

    private void process(final Reader commandSource) throws IOException {
        final String theLine = new BufferedReader(commandSource).readLine();
        List<String> lines = (theLine == null) ? List.empty() : List.of(theLine);
        lines.forEach(barcodeScannedListener::onBarcode);
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
