package ru.itis.service;

import java.util.List;
import ru.itis.storage.PhotoArchiveStorage;

public final class PhotoArchiveService {
    private final PhotoArchiveStorage storage;

    public PhotoArchiveService(PhotoArchiveStorage storage) {
        this.storage = storage;
    }

    public List<String> listAlbums() {
        return sorted(storage.listAlbums());
    }

    public List<String> listPhotos(String album) {
        return sorted(storage.listPhotos(album));
    }

    private List<String> sorted(List<String> names) {
        return names.stream().sorted(Utf8Order.COMPARATOR).toList();
    }
}
