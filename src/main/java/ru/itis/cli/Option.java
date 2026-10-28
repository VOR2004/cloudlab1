package ru.itis.cli;
import ru.itis.exception.InvalidArgumentsException;

public enum Option {
    ALBUM("--album"),
    PATH("--path"),
    PHOTO("--photo");

    private final String token;

    Option(String token) {
        this.token = token;
    }

    public static Option fromToken(String token) {
        for (Option option : values()) {
            if (option.token.equals(token)) return option;
        }
        throw new InvalidArgumentsException();
    }
}
