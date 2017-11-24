package ca.jbrains.pos.ui.test;

import ca.jbrains.pos.controller.test.NormalizeCommands;
import ca.jbrains.pos.controller.test.RemoveWhitespace;
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

        readThenProcessLines(List.empty());
    }

    @Test
    public void oneCommand() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode::");
        }});

        readThenProcessLines(List.of("::barcode::"));
    }

    @Test
    public void severalCommands() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode 1::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 2::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 3::");
        }});

        readThenProcessLines(List.of("::barcode 1::", "::barcode 2::", "::barcode 3::"));
    }

    private void readThenProcessLines(final Traversable<String> lines) throws IOException {
        interpretCommandsFromSource(readLines(lines));
    }

    private Reader readLines(final Traversable<String> lines) {
        return new StringReader(String.join(System.lineSeparator(), lines.toJavaList()));
    }

    private void interpretCommandsFromSource(final Reader commandSource) throws IOException {
        interpretCommands(readCommands(commandSource, new RemoveWhitespace()));
    }

    private void interpretCommands(final Traversable<String> commandsAsText) {
        commandsAsText.forEach(this::interpretCommand);
    }

    private Traversable<String> readCommands(final Reader commandSource, final NormalizeCommands normalizeCommands) {
        return normalizeCommands.normalizeCommands(linesFrom(commandSource));
    }

    private void interpretCommand(final String commandText) {
        // CONTRACT commandText is "normalized" (syntax is already correct)
        barcodeScannedListener.onBarcode(commandText);
    }

    private Stream<String> linesFrom(final Reader commandSource) {
        final BufferedReader bufferedCommandSource = new BufferedReader(commandSource);
        return Stream.ofAll(bufferedCommandSource.lines());
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
