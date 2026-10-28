package ru.itis;

import ru.itis.cli.CommandParser;

public final class Main {
    public static void main(String[] args) {
        CloudPhotoApplication application = new CloudPhotoApplication(new CommandParser());
        System.exit(application.run(args, System.getenv(), System.err));
    }
}
