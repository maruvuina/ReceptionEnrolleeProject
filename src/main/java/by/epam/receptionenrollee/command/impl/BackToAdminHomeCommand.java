package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.resource.ConfigurationManager;

import static by.epam.receptionenrollee.command.PagePath.ADMIN;

public class BackToAdminHomeCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        return ConfigurationManager.getProperty(ADMIN);
    }
}
