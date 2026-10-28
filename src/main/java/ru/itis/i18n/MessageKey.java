package ru.itis.i18n;

public enum MessageKey {
    MISSING_ENVIRONMENT("error.environment.missing"),
    INVALID_ARGUMENTS("error.arguments.invalid"),
    COMMAND_UNAVAILABLE("error.command.unavailable"),
    ALBUM_NOT_FOUND("error.album.notFound"),
    STORAGE_FAILURE("error.storage.failure");

    private final String resourceKey;

    MessageKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String resourceKey() {
        return resourceKey;
    }
}
