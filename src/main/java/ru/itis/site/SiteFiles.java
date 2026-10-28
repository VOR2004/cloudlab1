package ru.itis.site;

public final class SiteFiles {
    public static final String INDEX = "index.html";
    public static final String ERROR = "error.html";

    private SiteFiles() { }

    public static String album(int number) {
        return "album" + number + ".html";
    }
}
