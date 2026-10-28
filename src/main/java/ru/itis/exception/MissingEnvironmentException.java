package ru.itis.exception;

import ru.itis.i18n.MessageKey;

public final class MissingEnvironmentException extends CloudPhotoException {
    public MissingEnvironmentException() {
        super(MessageKey.MISSING_ENVIRONMENT);
    }
}
