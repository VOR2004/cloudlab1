package ru.itis.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import ru.itis.exception.NoPhotosException;
import ru.itis.exception.PhotoDirectoryException;
import ru.itis.model.PhotoNames;

public final class PhotoDirectory {
    public List<Path> photos(Path directory) {
        try {
            if (!Files.isDirectory(directory) || !Files.isReadable(directory)) {
                throw new PhotoDirectoryException(directory);
            }
            List<Path> photos;
            try (var entries = Files.list(directory)) {
                photos = entries
                        .filter(Files::isRegularFile)
                        .filter(path -> PhotoNames.isPhoto(path.getFileName().toString()))
                        .sorted((left, right) -> Utf8Order.COMPARATOR.compare(
                                left.getFileName().toString(), right.getFileName().toString()))
                        .toList();
            }
            if (photos.isEmpty()) {
                throw new NoPhotosException(directory);
            }
            for (Path photo : photos) {
                if (!Files.isReadable(photo)) {
                    throw new PhotoDirectoryException(directory);
                }
            }
            return photos;
        } catch (IOException | UncheckedIOException | SecurityException exception) {
            throw new PhotoDirectoryException(directory, exception);
        }
    }
}
