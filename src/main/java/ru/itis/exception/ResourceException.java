package ru.itis.exception;

import ru.itis.i18n.MessageKey;

public final class ResourceException extends CloudPhotoException {
    public ResourceException(String resource, Throwable cause) {
        super(MessageKey.RESOURCE_FAILURE, cause, resource);
    }
}
