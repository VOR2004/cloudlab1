package ru.itis.storage;

public enum StorageOperation {
    LIST_OBJECTS("ListObjectsV2"),
    UPLOAD_OBJECT("PutObject"),
    DOWNLOAD_OBJECT("GetObject"),
    DELETE_OBJECT("DeleteObject"),
    GET_POLICY("GetBucketPolicy"),
    SET_POLICY("PutBucketPolicy"),
    SET_WEBSITE("PutBucketWebsite");

    private final String apiName;

    StorageOperation(String apiName) {
        this.apiName = apiName;
    }

    public String apiName() {
        return apiName;
    }
}
