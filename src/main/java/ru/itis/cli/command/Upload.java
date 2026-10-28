package ru.itis.cli.command;

import java.nio.file.Path;

public record Upload(String album, Path path) implements Command {
}
