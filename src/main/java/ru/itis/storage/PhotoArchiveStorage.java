package ru.itis.storage;

import java.util.List;
import java.nio.file.Path;

public interface PhotoArchiveStorage extends AutoCloseable {
    List<String> listAlbums();
    List<String> listPhotos(String album);
    void createAlbum(String album);
    void uploadPhoto(String album, Path photo);

    @Override
    void close();
}
