package ru.itis.i18n;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class Messages {
    private static final ResourceBundle BUNDLE = loadBundle();

    private static ResourceBundle loadBundle() {
        var stream = Objects.requireNonNull(
                Messages.class.getResourceAsStream("/messages.properties"),
                "Missing resource: /messages.properties");
        try (var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return new PropertyResourceBundle(reader);
        } catch (IOException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
    private Messages() { }
    public static String get(MessageKey key) {
        return BUNDLE.getString(key.resourceKey());
    }
}
