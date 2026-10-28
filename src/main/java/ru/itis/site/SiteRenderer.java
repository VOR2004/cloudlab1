package ru.itis.site;

import java.util.List;
import ru.itis.resources.TextResources;
import ru.itis.storage.ObjectKeys;

public final class SiteRenderer {
    private final String emptyIndex = template("index-empty");
    private final String index = template("index");
    private final String albumLink = template("album-link");
    private final String emptyAlbum = template("album-empty");
    private final String album = template("album");
    private final String photo = template("photo");
    private final String error = template("error");

    public String index(List<String> sortedAlbums) {
        if (sortedAlbums.isEmpty()) {
            return emptyIndex;
        }
        StringBuilder links = new StringBuilder();
        for (int i = 0; i < sortedAlbums.size(); i++) {
            links.append(albumLink.formatted(SiteFiles.album(i + 1),
                    HtmlEncoding.text(sortedAlbums.get(i))));
        }
        return index.formatted(links);
    }

    public String album(String name, List<String> sortedPhotos) {
        String title = HtmlEncoding.text(name);
        if (sortedPhotos.isEmpty()) {
            return emptyAlbum.formatted(title, title);
        }
        StringBuilder images = new StringBuilder();
        for (String filename : sortedPhotos) {
            String url = HtmlEncoding.urlSegment(ObjectKeys.photoKey(name, filename));
            images.append(photo.formatted(HtmlEncoding.text(url), HtmlEncoding.text(filename)));
        }
        return album.formatted(title, title, images);
    }

    public String error() {
        return error;
    }

    private String template(String name) {
        return TextResources.read("/site/" + name + ".html");
    }
}
