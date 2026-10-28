package ru.itis.exception;

import ru.itis.i18n.MessageKey;

public final class InvalidArgumentsException extends CloudPhotoException {
    public InvalidArgumentsException() {
        super(MessageKey.INVALID_ARGUMENTS);
    }

    public InvalidArgumentsException(Throwable cause) {
        super(MessageKey.INVALID_ARGUMENTS, cause);
    }
}
