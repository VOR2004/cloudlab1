package ru.itis.storage;

import java.util.ArrayList;
import ru.itis.resources.TextResources;
import software.amazon.awssdk.policybuilder.iam.IamPolicy;
import software.amazon.awssdk.policybuilder.iam.IamStatement;

public final class WebsitePolicy {
    private static final String PUBLIC_READ_ID = "CloudPhotoPublicRead";

    private WebsitePolicy() { }

    public static String withPublicRead(String existingJson, String bucket) {
        IamPolicy existing = IamPolicy.fromJson(existingJson);
        var statements = new ArrayList<IamStatement>();
        existing.statements().stream()
                .filter(statement -> !PUBLIC_READ_ID.equals(statement.sid()))
                .forEach(statements::add);
        IamPolicy publicRead = IamPolicy.fromJson(
                TextResources.read("/storage/public-read-policy.json").formatted(bucket));
        statements.addAll(publicRead.statements());
        return existing.copy(builder -> builder.statements(statements)).toJson();
    }
}
