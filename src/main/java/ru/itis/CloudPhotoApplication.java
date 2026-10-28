package ru.itis;

import java.io.PrintStream;
import java.util.Map;
import ru.itis.cli.CommandParser;
import ru.itis.config.StorageConfig;
import ru.itis.exception.CloudPhotoException;
import ru.itis.exception.CommandUnavailableException;

public final class CloudPhotoApplication {
    private final CommandParser parser;

    public CloudPhotoApplication(CommandParser parser) {
        this.parser = parser;
    }

    public int run(String[] args, Map<String, String> environment, PrintStream error) {
        try {
            StorageConfig.fromEnvironment(environment);
            parser.parse(args);
            throw new CommandUnavailableException();
        } catch (CloudPhotoException exception) {
            error.println(exception.getMessage());
            return 1;
        }
    }
}
