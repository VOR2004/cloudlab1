package ru.itis.cli.command;

import java.nio.file.Path;

public record Download(String album, Path path) implements Command {
}
