package ru.itis.config;

import java.util.Map;
import ru.itis.exception.MissingEnvironmentException;

public final class StorageConfig {
    private final String bucket;
    private final String accessKeyId;
    private final String secretAccessKey;

    private StorageConfig(String bucket, String accessKeyId, String secretAccessKey) {
        this.bucket = bucket;
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
    }

    public static StorageConfig fromEnvironment(Map<String, String> environment) {
        return new StorageConfig(
                required(environment, "BUCKET"),
                required(environment, "AWS_ACCESS_KEY_ID"),
                required(environment, "AWS_SECRET_ACCESS_KEY")
        );
    }

    private static String required(Map<String, String> environment, String name) {
        String value = environment.get(name);
        if (value == null || value.isEmpty()) {
            throw new MissingEnvironmentException();
        }
        return value;
    }

    public String bucket() {
        return bucket;
    }

    public String accessKeyId() {
        return accessKeyId;
    }

    public String secretAccessKey() {
        return secretAccessKey;
    }
}
