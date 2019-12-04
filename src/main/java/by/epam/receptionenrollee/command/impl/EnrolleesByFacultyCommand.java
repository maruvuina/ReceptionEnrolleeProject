package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.resource.ConfigurationManager;
import by.epam.receptionenrollee.validator.Validator;

import static by.epam.receptionenrollee.command.PagePath.ENROLLEES_BY_FACULTY;

public class EnrolleesByFacultyCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        Validator validator = new Validator();
        page = validator.getEnrolleesByFacultyPage(sessionRequestContent);
        return page;
    }
}
