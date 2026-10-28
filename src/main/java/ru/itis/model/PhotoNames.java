package ru.itis.model;

public final class PhotoNames {
    private PhotoNames() { }

    public static boolean isPhoto(String name) {
        return name.regionMatches(true, name.length() - 4, ".jpg", 0, 4)
                || name.regionMatches(true, name.length() - 5, ".jpeg", 0, 5);
    }
}
