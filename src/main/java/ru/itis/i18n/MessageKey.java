package ru.itis.i18n;

public enum MessageKey {
    MISSING_ENVIRONMENT("error.environment.missing"),
    INVALID_ARGUMENTS("error.arguments.invalid"),
    COMMAND_UNAVAILABLE("error.command.unavailable");

    private final String resourceKey;

    MessageKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String resourceKey() {
        return resourceKey;
    }
}
