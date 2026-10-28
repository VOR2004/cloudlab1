package ru.itis.exception;

import java.nio.file.Path;
import ru.itis.i18n.MessageKey;

public final class PhotoWriteException extends CloudPhotoException {
    public PhotoWriteException(Path directory, Throwable cause) {
        super(MessageKey.PHOTO_WRITE_FAILURE, cause, directory.toString());
    }
}
