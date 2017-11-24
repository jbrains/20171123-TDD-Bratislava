package ca.jbrains.pos.controller.test;

import io.vavr.collection.Traversable;

public class RemoveWhitespace implements NormalizeCommands {
    @Override
    public Traversable<String> normalizeCommands(final Traversable<String> lines) {
        return lines.map(line -> line.trim()).filter(line -> !line.isEmpty());
    }
}
