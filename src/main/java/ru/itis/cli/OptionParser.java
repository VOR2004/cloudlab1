package ru.itis.cli;

import java.util.EnumMap;
import java.util.Map;
import ru.itis.exception.InvalidArgumentsException;

final class OptionParser {
    Map<Option, String> parse(String[] args, CommandType type) {
        Map<Option, String> options = new EnumMap<>(Option.class);
        for (int i = 1; i < args.length; i += 2) {
            Option option = Option.fromToken(args[i]);
            if (!type.allowedOptions().contains(option) || options.containsKey(option)
                    || i + 1 >= args.length || args[i + 1].isEmpty()) {
                throw new InvalidArgumentsException();
            }
            options.put(option, args[i + 1]);
        }
        if (!options.keySet().containsAll(type.requiredOptions())) {
            throw new InvalidArgumentsException();
        }
        return options;
    }
}
