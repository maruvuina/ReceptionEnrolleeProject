package by.epam.receptionenrollee.command;

import java.util.Arrays;
import java.util.Optional;

public class ActionFactory {
    private static final ActionFactory INSTANCE = new ActionFactory();

    private ActionFactory() {}

    public static ActionFactory getInstance() {
        return INSTANCE;
    }

    public Optional<ActionCommand> defineCommand(String commandName) {
        return Arrays.stream(CommandType.values())
                .filter(command -> command.name().equalsIgnoreCase(commandName))
                .map(CommandType::getCommand)
                .findAny();
    }
}
