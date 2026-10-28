package ru.itis.site;

import java.util.List;
import ru.itis.service.Utf8Order;
import ru.itis.storage.PhotoArchiveStorage;

public final class SitePublisher {
    private final PhotoArchiveStorage storage;
    private final SiteRenderer renderer;

    public SitePublisher(PhotoArchiveStorage storage, SiteRenderer renderer) {
        this.storage = storage;
        this.renderer = renderer;
    }

    public void publish() {
        List<String> albums = storage.listAlbums().stream().sorted(Utf8Order.COMPARATOR).toList();
        storage.configureWebsite();
        for (int i = 0; i < albums.size(); i++) {
            String name = albums.get(i);
            List<String> photos = storage.listPhotos(name).stream().sorted(Utf8Order.COMPARATOR).toList();
            storage.publishPage(SiteFiles.album(i + 1), renderer.album(name, photos));
        }
        storage.publishPage(SiteFiles.ERROR, renderer.error());
        storage.publishPage(SiteFiles.INDEX, renderer.index(albums));
    }
}
