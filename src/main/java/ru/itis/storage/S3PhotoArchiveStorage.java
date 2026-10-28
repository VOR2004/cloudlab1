package ru.itis.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import ru.itis.site.SiteFiles;
import ru.itis.exception.AlbumNotFoundException;
import ru.itis.exception.StorageException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.GetBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.PutBucketWebsiteRequest;
import software.amazon.awssdk.services.s3.model.WebsiteConfiguration;
import software.amazon.awssdk.services.s3.model.IndexDocument;
import software.amazon.awssdk.services.s3.model.ErrorDocument;

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

    @Override
    public void createAlbum(String album) {
        try {
            client.putObject(PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(ObjectKeys.albumPrefix(album))
                    .contentType("application/x-directory")
                    .build(), RequestBody.empty());
        } catch (SdkException exception) {
            throw new StorageException(StorageOperation.UPLOAD_OBJECT, exception);
        }
    }

    @Override
    public void uploadPhoto(String album, Path photo) {
        try {
            client.putObject(PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(ObjectKeys.photoKey(album, photo.getFileName().toString()))
                    .contentType("image/jpeg")
                    .build(), RequestBody.fromFile(photo));
        } catch (SdkException | UncheckedIOException exception) {
            throw new StorageException(StorageOperation.UPLOAD_OBJECT, exception);
        }
    }

    @Override
    public void downloadPhoto(String album, String photo, Path destination) {
        try {
            client.getObject(GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(ObjectKeys.photoKey(album, photo))
                    .build(), (response, input) -> {
                        Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
                        return response;
                    });
        } catch (SdkException exception) {
            throw new StorageException(StorageOperation.DOWNLOAD_OBJECT, exception);
        }
    }

    @Override
    public void deletePhoto(String album, String photo) {
        deleteKey(ObjectKeys.photoKey(album, photo));
    }

    @Override
    public void deleteAlbumMarker(String album) {
        deleteKey(ObjectKeys.albumPrefix(album));
    }

    @Override
    public void configureWebsite() {
        String existingPolicy;
        try {
            existingPolicy = client.getBucketPolicy(GetBucketPolicyRequest.builder()
                    .bucket(bucket).build()).policy();
        } catch (SdkException exception) {
            throw new StorageException(StorageOperation.GET_POLICY, exception);
        }
        String policy = WebsitePolicy.withPublicRead(existingPolicy, bucket);
        try {
            client.putBucketPolicy(PutBucketPolicyRequest.builder()
                    .bucket(bucket).policy(policy).build());
        } catch (SdkException exception) {
            throw new StorageException(StorageOperation.SET_POLICY, exception);
        }
        try {
            client.putBucketWebsite(PutBucketWebsiteRequest.builder()
                    .bucket(bucket)
                    .websiteConfiguration(WebsiteConfiguration.builder()
                            .indexDocument(IndexDocument.builder().suffix(SiteFiles.INDEX).build())
                            .errorDocument(ErrorDocument.builder().key(SiteFiles.ERROR).build())
                            .build())
                    .build());
        } catch (SdkException exception) {
            throw new StorageException(StorageOperation.SET_WEBSITE, exception);
        }
    }

    @Override
    public void publishPage(String key, String html) {
        try {
            client.putObject(PutObjectRequest.builder()
                    .bucket(bucket).key(key)
                    .contentType("text/html; charset=UTF-8")
                    .cacheControl("no-cache")
                    .build(), RequestBody.fromString(html, StandardCharsets.UTF_8));
        } catch (SdkException exception) {
            throw new StorageException(StorageOperation.UPLOAD_OBJECT, exception);
        }
    }

    private void deleteKey(String key) {
        try {
            client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build());
        } catch (SdkException exception) {
            throw new StorageException(StorageOperation.DELETE_OBJECT, exception);
        }
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
            throw new StorageException(StorageOperation.LIST_OBJECTS, exception);
        }
    }

    @Override
    public void close() {
        client.close();
    }
}
