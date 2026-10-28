package ru.itis.service;

import java.util.List;
import java.nio.file.Path;
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

    public void upload(String album, Path directory) {
        List<Path> photos = new PhotoDirectory().photos(directory);
        storage.createAlbum(album);
        for (Path photo : photos) {
            storage.uploadPhoto(album, photo);
        }
    }

    private List<String> sorted(List<String> names) {
        return names.stream().sorted(Utf8Order.COMPARATOR).toList();
    }
}
