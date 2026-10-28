package ru.itis.exception;

import ru.itis.i18n.MessageKey;

public final class StorageException extends CloudPhotoException {
    public StorageException(Throwable cause) {
        super(MessageKey.STORAGE_FAILURE, cause);
    }
}
