package ru.itis.storage;

import java.util.Optional;

public final class ObjectKeys {
    public static final String ALBUMS_PREFIX = "albums/";

    private ObjectKeys() { }

    public static String albumPrefix(String album) {
        return ALBUMS_PREFIX + album + "/";
    }

    public static Optional<String> albumFromMarker(String key) {
        if (!key.startsWith(ALBUMS_PREFIX) || !key.endsWith("/")
                || key.length() <= ALBUMS_PREFIX.length()) {
            return Optional.empty();
        }
        String album = key.substring(ALBUMS_PREFIX.length(), key.length() - 1);
        return !album.isEmpty() && !album.contains("/") ? Optional.of(album) : Optional.empty();
    }

    public static Optional<String> photoFromKey(String album, String key) {
        String prefix = albumPrefix(album);
        if (!key.startsWith(prefix)) {
            return Optional.empty();
        }
        String name = key.substring(prefix.length());
        boolean jpeg = name.regionMatches(true, name.length() - 4, ".jpg", 0, 4)
                || name.regionMatches(true, name.length() - 5, ".jpeg", 0, 5);
        return !name.contains("/") && jpeg ? Optional.of(name) : Optional.empty();
    }
}
