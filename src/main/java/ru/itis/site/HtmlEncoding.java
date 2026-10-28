package ru.itis.site;

import java.nio.charset.StandardCharsets;

public final class HtmlEncoding {
    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    private HtmlEncoding() { }

    public static String text(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }

    public static String urlSegment(String value) {
        StringBuilder result = new StringBuilder();
        for (byte item : value.getBytes(StandardCharsets.UTF_8)) {
            int b = Byte.toUnsignedInt(item);
            if (b >= 'a' && b <= 'z' || b >= 'A' && b <= 'Z'
                    || b >= '0' && b <= '9' || b == '-' || b == '_' || b == '.' || b == '~') {
                result.append((char) b);
            } else {
                result.append('%').append(HEX[b >>> 4]).append(HEX[b & 15]);
            }
        }
        return result.toString();
    }
}
