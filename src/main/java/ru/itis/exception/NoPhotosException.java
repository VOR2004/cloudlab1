package ru.itis.exception;

import java.nio.file.Path;
import ru.itis.i18n.MessageKey;

public final class NoPhotosException extends CloudPhotoException {
    public NoPhotosException(Path directory) {
        super(MessageKey.NO_PHOTOS, directory.toString());
    }
}
