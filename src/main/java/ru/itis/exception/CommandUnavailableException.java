package ru.itis.exception;

import ru.itis.i18n.MessageKey;

public final class CommandUnavailableException extends CloudPhotoException {
    public CommandUnavailableException() {
        super(MessageKey.COMMAND_UNAVAILABLE);
    }
}
