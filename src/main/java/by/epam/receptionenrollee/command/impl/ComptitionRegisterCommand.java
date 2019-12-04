package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.validator.Validator;

public class ComptitionRegisterCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        Validator validator = new Validator();
        String page;
        page = validator.getRegisterEnrolleePage(sessionRequestContent);
        return page;
    }
}
