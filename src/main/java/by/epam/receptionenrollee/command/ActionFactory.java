package by.epam.receptionenrollee.command;

import by.epam.receptionenrollee.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static by.epam.receptionenrollee.command.RequestParam.PARAM_NAME_LANGUAGE_SITE;

public class ActionFactory {
    private static final ActionFactory INSTANCE = new ActionFactory();

    private ActionFactory() {}

    public static ActionFactory getInstance() {
        return INSTANCE;
    }

    public ActionCommand defineCommand(HttpServletRequest request) {
        String commandName = request.getParameter("command");
        return Arrays.stream(CommandType.values())
                .filter(command -> command.name().equalsIgnoreCase(commandName))
                .map(CommandType::getCommand)
                .findAny()
                .orElseGet(() -> {
                    request.setAttribute("wrongAction",
                            request.getParameter("command")
                                    + new MessageManager((String) request
                                    .getSession()
                                    .getAttribute(PARAM_NAME_LANGUAGE_SITE)).getProperty("message.wrongaction"));
                    return CommandType.LOGIN.getCommand();
                });
    }
}
