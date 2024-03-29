package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.service.SessionRequestContent;
import by.epam.receptionenrollee.service.DefaultService;

public class LogoutCommand implements ActionCommand {
    private DefaultService receiver;

    public LogoutCommand() {
        this.receiver = new DefaultService();
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        sessionRequestContent.setSessionInvalidate(true);
        return receiver.getPath();
    }
}
