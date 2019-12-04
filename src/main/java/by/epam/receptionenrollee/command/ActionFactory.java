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

    //TODO
//    public ActionCommand defineCommand2(HttpServletRequest request) {
//        ActionCommand current = new EmptyCommand();
//        String action = request.getParameter("command");
//
//        if (action == null || action.isEmpty()) {
//            return current;
//        }
//        try {
//            CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
//            current = currentEnum.getCommand();
//        } catch (IllegalArgumentException e) {
//            request.setAttribute("wrongAction", action
//                    + MessageManager.getProperty("message.wrongaction"));
//        }
//        return current;
//    }
}
