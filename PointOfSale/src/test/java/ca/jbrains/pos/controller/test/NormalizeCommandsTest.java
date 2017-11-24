package ca.jbrains.pos.controller.test;

import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.Test;

public class NormalizeCommandsTest {

    private final RemoveWhitespace removeWhitespace = new RemoveWhitespace();

    @Test
    public void blankLines() throws Exception {
        Assert.assertEquals(
                List.of(
                        "::barcode 1::",
                        "::barcode 2::",
                        "::barcode 3::"
                ),
                removeWhitespace.normalizeCommands(List.of(
                        "",
                        "::barcode 1::",
                        "",
                        "::barcode 2::",
                        "",
                        "::barcode 3::",
                        "")
                )
        );
    }

    @Test
    public void extraWhitespaceAroundCommands() throws Exception {
        Assert.assertEquals(
                List.of(
                        "::before::",
                        "::after::",
                        "::around::",
                        "::tabs::",
                        "::everything::"),
                removeWhitespace.normalizeCommands(List.of(
                        " ::before::",
                        "::after:: ",
                        " ::around:: ",
                        "\t::tabs::\t",
                        "  \f  \f   \t    ::everything::  \t   \t    \n    \n \r")
                )
        );
    }
}
