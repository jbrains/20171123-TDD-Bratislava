package ca.jbrains.pos.controller.test;

import io.vavr.collection.Traversable;

public interface NormalizeCommands {
    Traversable<String> normalizeCommands(Traversable<String> lines);
}
