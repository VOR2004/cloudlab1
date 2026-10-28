package ru.itis.exception;

import ru.itis.i18n.MessageKey;

public final class AlbumNotFoundException extends CloudPhotoException {
    public AlbumNotFoundException(String album) {
        super(MessageKey.ALBUM_NOT_FOUND, album);
    }
}
