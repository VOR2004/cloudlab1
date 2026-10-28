package ru.itis.exception;

import ru.itis.i18n.MessageKey;
import ru.itis.storage.StorageOperation;
import software.amazon.awssdk.awscore.exception.AwsServiceException;

public final class StorageException extends CloudPhotoException {
    public StorageException(Throwable cause) {
        super(MessageKey.STORAGE_FAILURE, cause, diagnostic(cause));
    }

    public StorageException(StorageOperation operation, Throwable cause) {
        super(MessageKey.STORAGE_OPERATION_FAILURE, cause, operation.apiName(), diagnostic(cause));
    }

    private static String diagnostic(Throwable cause) {
        if (cause instanceof AwsServiceException service) {
            String code = service.awsErrorDetails() == null
                    ? null : service.awsErrorDetails().errorCode();
            return "HTTP " + service.statusCode() + "; "
                    + (code != null && code.matches("[A-Za-z0-9_.-]{1,100}")
                    ? code : service.getClass().getSimpleName());
        }
        Throwable root = cause;
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }
        return root.getClass().getSimpleName();
    }
}
