package ru.itis.storage;

import java.net.URI;
import java.time.Duration;
import ru.itis.config.StorageConfig;
import ru.itis.exception.StorageException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public final class S3StorageFactory {
    private static final URI ENDPOINT = URI.create("https://storage.yandexcloud.net");
    private static final Region REGION = Region.of("ru-central1");

    private S3StorageFactory() { }

    public static PhotoArchiveStorage create(StorageConfig config) {
        try {
            S3Client client = S3Client.builder()
                    .endpointOverride(ENDPOINT)
                    .region(REGION)
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(config.accessKeyId(), config.secretAccessKey())))
                    .forcePathStyle(true)
                    .httpClientBuilder(UrlConnectionHttpClient.builder()
                            .connectionTimeout(Duration.ofSeconds(10))
                            .socketTimeout(Duration.ofSeconds(30)))
                    .overrideConfiguration(builder -> builder
                            .apiCallTimeout(Duration.ofSeconds(60)))
                    .build();
            return new S3PhotoArchiveStorage(client, config.bucket());
        } catch (SdkException exception) {
            throw new StorageException(exception);
        }
    }
}
