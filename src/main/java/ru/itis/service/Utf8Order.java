package ru.itis.service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

public final class Utf8Order {
    public static final Comparator<String> COMPARATOR = (left, right) ->
            Arrays.compareUnsigned(left.getBytes(StandardCharsets.UTF_8),
                    right.getBytes(StandardCharsets.UTF_8));

    private Utf8Order() { }
}
