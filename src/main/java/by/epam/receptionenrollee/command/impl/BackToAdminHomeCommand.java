package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.resource.ConfigurationManager;

import static by.epam.receptionenrollee.command.PagePath.ADMIN;
import static by.epam.receptionenrollee.command.RequestParam.*;
import static by.epam.receptionenrollee.command.RequestParam.PARAM_NAME_ROLE;

public class BackToAdminHomeCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_FIRST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_FIRST_NAME));
        sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_LAST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_LAST_NAME));
        sessionRequestContent.setSessionAttribute(PARAM_NAME_ROLE, sessionRequestContent.getParameter(PARAM_NAME_ROLE));
        return ConfigurationManager.getProperty(ADMIN);
    }
}
