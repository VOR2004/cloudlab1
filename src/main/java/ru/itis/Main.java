package ru.itis;

import ru.itis.cli.CommandParser;
import ru.itis.storage.S3StorageFactory;

public final class Main {
    public static void main(String[] args) {
        CloudPhotoApplication application = new CloudPhotoApplication(
                new CommandParser(), S3StorageFactory::create);
        System.exit(application.run(args, System.getenv(), System.out, System.err));
    }
}
