package ru.itis.i18n;

public enum MessageKey {
    MISSING_ENVIRONMENT("error.environment.missing"),
    INVALID_ARGUMENTS("error.arguments.invalid"),
    ALBUM_NOT_FOUND("error.album.notFound"),
    STORAGE_FAILURE("error.storage.failure"),
    PHOTO_DIRECTORY_UNAVAILABLE("error.directory.unavailable"),
    NO_PHOTOS("error.photos.absent"),
    PHOTO_WRITE_FAILURE("error.photo.write"),
    PHOTO_NOT_FOUND("error.photo.notFound"),
    RESOURCE_FAILURE("error.resource.failure"),
    STORAGE_OPERATION_FAILURE("error.storage.operation");

    private final String resourceKey;

    MessageKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String resourceKey() {
        return resourceKey;
    }
}
