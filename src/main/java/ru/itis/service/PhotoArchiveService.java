package ru.itis.service;

import java.util.List;
import java.nio.file.Path;
import ru.itis.storage.PhotoArchiveStorage;
import ru.itis.exception.PhotoNotFoundException;

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

    public void download(String album, Path directory) {
        DownloadDirectory destination = new DownloadDirectory(directory);
        List<String> photos = listPhotos(album);
        for (String photo : photos) {
            destination.save(photo, temporary -> storage.downloadPhoto(album, photo, temporary));
        }
    }

    public void deletePhoto(String album, String photo) {
        if (!listPhotos(album).contains(photo)) {
            throw new PhotoNotFoundException(photo);
        }
        storage.deletePhoto(album, photo);
    }

    public void deleteAlbum(String album) {
        List<String> photos = listPhotos(album);
        for (String photo : photos) {
            storage.deletePhoto(album, photo);
        }
        storage.deleteAlbumMarker(album);
    }

    private List<String> sorted(List<String> names) {
        return names.stream().sorted(Utf8Order.COMPARATOR).toList();
    }
}
