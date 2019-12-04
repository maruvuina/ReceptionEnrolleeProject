package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

import static by.epam.receptionenrollee.command.PagePath.LOGIN;

public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        return ConfigurationManager.getProperty(LOGIN);
    }
}
