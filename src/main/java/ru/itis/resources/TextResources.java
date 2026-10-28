package ru.itis.resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import ru.itis.exception.ResourceException;

public final class TextResources {
    private TextResources() { }

    public static String read(String name) {
        try (var stream = TextResources.class.getResourceAsStream(name)) {
            if (stream == null) {
                throw new ResourceException(name, null);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new ResourceException(name, exception);
        }
    }
}
