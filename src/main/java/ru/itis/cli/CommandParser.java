package ru.itis.cli;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Map;
import ru.itis.cli.command.Command;
import ru.itis.cli.command.DeleteAlbum;
import ru.itis.cli.command.DeletePhoto;
import ru.itis.cli.command.Download;
import ru.itis.cli.command.ListAlbums;
import ru.itis.cli.command.ListPhotos;
import ru.itis.cli.command.MakeSite;
import ru.itis.cli.command.Upload;
import ru.itis.exception.InvalidArgumentsException;

public final class CommandParser {
    private final OptionParser optionParser = new OptionParser();

    public Command parse(String[] args) {
        if (args.length == 0) {
            throw new InvalidArgumentsException();
        }
        CommandType type = CommandType.fromToken(args[0]);
        Map<Option, String> options = optionParser.parse(args, type);
        String album = options.get(Option.ALBUM);
        String photo = options.get(Option.PHOTO);
        validateName(album);
        validateName(photo);

        return switch (type) {
            case LIST -> album == null ? new ListAlbums() : new ListPhotos(album);
            case UPLOAD -> new Upload(album, parsePath(options));
            case DOWNLOAD -> new Download(album, parsePath(options));
            case DELETE -> photo == null ? new DeleteAlbum(album) : new DeletePhoto(album, photo);
            case MKSITE -> new MakeSite();
        };
    }

    private void validateName(String name) {
        if (name != null && (name.contains("/") || name.indexOf('\0') >= 0)) {
            throw new InvalidArgumentsException();
        }
    }

    private Path parsePath(Map<Option, String> options) {
        try {
            return Path.of(options.getOrDefault(Option.PATH, "."));
        } catch (InvalidPathException exception) {
            throw new InvalidArgumentsException(exception);
        }
    }
}
