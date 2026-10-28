package ru.itis.exception;

import ru.itis.i18n.MessageKey;

public final class PhotoNotFoundException extends CloudPhotoException {
    public PhotoNotFoundException(String photo) {
        super(MessageKey.PHOTO_NOT_FOUND, photo);
    }
}
