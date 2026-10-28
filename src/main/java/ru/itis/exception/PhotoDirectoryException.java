package ru.itis.exception;

import java.nio.file.Path;
import ru.itis.i18n.MessageKey;

public final class PhotoDirectoryException extends CloudPhotoException {
    public PhotoDirectoryException(Path directory) {
        super(MessageKey.PHOTO_DIRECTORY_UNAVAILABLE, directory.toString());
    }

    public PhotoDirectoryException(Path directory, Throwable cause) {
        super(MessageKey.PHOTO_DIRECTORY_UNAVAILABLE, cause, directory.toString());
    }
}
