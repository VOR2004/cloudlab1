package ru.itis.cli;
import java.util.Set;
import ru.itis.exception.InvalidArgumentsException;

public enum CommandType {
    LIST("list", Set.of(Option.ALBUM), Set.of()),
    UPLOAD("upload", Set.of(Option.ALBUM, Option.PATH), Set.of(Option.ALBUM)),
    DOWNLOAD("download", Set.of(Option.ALBUM, Option.PATH), Set.of(Option.ALBUM)),
    DELETE("delete", Set.of(Option.ALBUM, Option.PHOTO), Set.of(Option.ALBUM)),
    MKSITE("mksite", Set.of(), Set.of());

    private final String token;
    private final Set<Option> allowed;
    private final Set<Option> required;

    CommandType(String token, Set<Option> allowed, Set<Option> required) {
        this.token = token;
        this.allowed = allowed;
        this.required = required;
    }

    public Set<Option> allowedOptions() {
        return allowed;
    }

    public Set<Option> requiredOptions() {
        return required;
    }

    public static CommandType fromToken(String token) {
        for (CommandType type : values()) {
            if (type.token.equals(token)) {
                return type;
            }
        }
        throw new InvalidArgumentsException();
    }
}
