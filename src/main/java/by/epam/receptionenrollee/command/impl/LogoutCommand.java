package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.service.SessionRequestContent;
import by.epam.receptionenrollee.service.DefaultService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class LogoutCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);
    private DefaultService receiver = new DefaultService();

    public LogoutCommand() {}

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        sessionRequestContent.setSessionInvalidate(true);
        return receiver.getPath();
    }
}
