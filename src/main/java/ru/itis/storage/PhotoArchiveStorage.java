package ru.itis.storage;

import java.util.List;

public interface PhotoArchiveStorage extends AutoCloseable {
    List<String> listAlbums();
    List<String> listPhotos(String album);

    @Override
    void close();
}
