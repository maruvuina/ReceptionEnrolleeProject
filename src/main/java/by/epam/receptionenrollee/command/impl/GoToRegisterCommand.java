package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.service.SessionRequestContent;
import by.epam.receptionenrollee.resource.ConfigurationManager;

import static by.epam.receptionenrollee.command.PagePath.GO_TO_REGISTER;

public class GoToRegisterCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        return ConfigurationManager.getProperty(GO_TO_REGISTER);
    }
}
