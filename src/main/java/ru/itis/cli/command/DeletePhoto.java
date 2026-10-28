package ru.itis.cli.command;

public record DeletePhoto(String album, String photo) implements Command {
}
