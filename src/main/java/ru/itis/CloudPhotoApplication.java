package ru.itis;

import java.io.PrintStream;
import java.util.Map;
import java.util.function.Function;
import ru.itis.cli.CommandExecutor;
import ru.itis.cli.CommandParser;
import ru.itis.cli.command.MakeSite;
import ru.itis.config.StorageConfig;
import ru.itis.exception.CloudPhotoException;
import ru.itis.exception.CommandUnavailableException;
import ru.itis.service.PhotoArchiveService;
import ru.itis.storage.PhotoArchiveStorage;

public final class CloudPhotoApplication {
    private final CommandParser parser;
    private final Function<StorageConfig, PhotoArchiveStorage> storageFactory;

    public CloudPhotoApplication(
            CommandParser parser,
            Function<StorageConfig, PhotoArchiveStorage> storageFactory
    ) {
        this.parser = parser;
        this.storageFactory = storageFactory;
    }

    public int run(String[] args, Map<String, String> environment,
                   PrintStream output, PrintStream error) {
        try {
            StorageConfig config = StorageConfig.fromEnvironment(environment);
            var command = parser.parse(args);
            if (command instanceof MakeSite) {
                throw new CommandUnavailableException();
            }
            try (PhotoArchiveStorage storage = storageFactory.apply(config)) {
                var service = new PhotoArchiveService(storage);
                new CommandExecutor(service, output).execute(command);
            }
            return 0;
        } catch (CloudPhotoException exception) {
            error.println(exception.getMessage());
            return 1;
        }
    }
}
