package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.validator.Validator;

public class LoginCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        Validator validator = new Validator();
        page = validator.getVerifiedUserPage(sessionRequestContent);
        return page;
    }
}
