package ru.itis.exception;
import ru.itis.i18n.Messages;
import ru.itis.i18n.MessageKey;

public abstract class CloudPhotoException extends RuntimeException {
    protected CloudPhotoException(MessageKey key) {
        super(Messages.get(key));
    }

    protected CloudPhotoException(MessageKey key, Throwable cause) {
        super(Messages.get(key), cause);
    }
}
