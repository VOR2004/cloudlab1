package ru.itis.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ru.itis.exception.AlbumNotFoundException;
import ru.itis.exception.StorageException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;

public final class S3PhotoArchiveStorage implements PhotoArchiveStorage {
    private final S3Client client;
    private final String bucket;

    public S3PhotoArchiveStorage(S3Client client, String bucket) {
        this.client = client;
        this.bucket = bucket;
    }

    @Override
    public List<String> listAlbums() {
        List<String> albums = new ArrayList<>();
        forEachKey(ObjectKeys.ALBUMS_PREFIX,
                key -> ObjectKeys.albumFromMarker(key).ifPresent(albums::add));
        return albums;
    }

    @Override
    public List<String> listPhotos(String album) {
        String prefix = ObjectKeys.albumPrefix(album);
        List<String> keys = new ArrayList<>();
        forEachKey(prefix, keys::add);
        if (!keys.contains(prefix)) {
            throw new AlbumNotFoundException(album);
        }
        return keys.stream()
                .flatMap(key -> ObjectKeys.photoFromKey(album, key).stream())
                .toList();
    }

    private void forEachKey(String prefix, Consumer<String> consumer) {
        try {
            String token = null;
            do {
                var request = ListObjectsV2Request.builder()
                        .bucket(bucket)
                        .prefix(prefix)
                        .continuationToken(token)
                        .build();
                var response = client.listObjectsV2(request);
                response.contents().forEach(object -> consumer.accept(object.key()));
                token = Boolean.TRUE.equals(response.isTruncated())
                        ? response.nextContinuationToken() : null;
            } while (token != null);
        } catch (SdkException exception) {
            throw new StorageException(exception);
        }
    }

    @Override
    public void close() {
        client.close();
    }
}
