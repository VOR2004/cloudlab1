package ru.itis.cli;

import java.io.PrintStream;
import ru.itis.cli.command.Command;
import ru.itis.cli.command.ListAlbums;
import ru.itis.cli.command.ListPhotos;
import ru.itis.exception.CommandUnavailableException;
import ru.itis.service.PhotoArchiveService;

public final class CommandExecutor {
    private final PhotoArchiveService service;
    private final PrintStream output;

    public CommandExecutor(PhotoArchiveService service, PrintStream output) {
        this.service = service;
        this.output = output;
    }

    public void execute(Command command) {
        switch (command) {
            case ListAlbums ignored -> service.listAlbums().forEach(output::println);
            case ListPhotos photos -> service.listPhotos(photos.album()).forEach(output::println);
            default -> throw new CommandUnavailableException();
        }
    }
}
