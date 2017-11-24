package ca.jbrains.pos.ui.test;

import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.collection.Traversable;
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

        process(readLines(List.of("::barcode::")));
    }

    @Test
    public void severalCommands() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode 1::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 2::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 3::");
        }});

        process(readLines(List.of("::barcode 1::", "::barcode 2::", "::barcode 3::")));
    }

    private Reader readLines(final List<String> lines) {
        return new StringReader(String.join(System.lineSeparator(), lines.toJavaList()));
    }

    private void process(final Reader commandSource) throws IOException {
        Traversable<String> lines = linesFrom(commandSource);
        lines.forEach(barcodeScannedListener::onBarcode);
    }

    private Traversable<String> linesFrom(final Reader commandSource) throws IOException {
        final BufferedReader bufferedCommandSource = new BufferedReader(commandSource);
        return Stream.ofAll(bufferedCommandSource.lines());
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
