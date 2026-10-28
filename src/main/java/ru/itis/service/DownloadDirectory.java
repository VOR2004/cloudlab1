package ru.itis.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;
import ru.itis.exception.PhotoDirectoryException;
import ru.itis.exception.PhotoWriteException;

public final class DownloadDirectory {
    private final Path directory;

    public DownloadDirectory(Path directory) {
        try {
            this.directory = directory.toRealPath();
            if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
                throw new PhotoDirectoryException(directory);
            }
        } catch (IOException | SecurityException exception) {
            throw new PhotoDirectoryException(directory, exception);
        }
    }

    public void save(String name, Consumer<Path> download) {
        Path temporary = null;
        try {
            Path filename = Path.of(name);
            Path target = directory.resolve(filename).normalize();
            if (filename.isAbsolute() || filename.getNameCount() != 1
                    || !directory.equals(target.getParent())) {
                throw new IOException();
            }
            temporary = Files.createTempFile(directory, ".cloudphoto-", ".part");
            download.accept(temporary);
            Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException | IllegalArgumentException | SecurityException exception) {
            cleanup(temporary, exception);
            throw new PhotoWriteException(directory, exception);
        } catch (RuntimeException exception) {
            cleanup(temporary, exception);
            throw exception;
        }
    }

    private void cleanup(Path temporary, Exception original) {
        if (temporary == null) {
            return;
        }
        try {
            Files.deleteIfExists(temporary);
        } catch (IOException | SecurityException exception) {
            original.addSuppressed(exception);
        }
    }
}
