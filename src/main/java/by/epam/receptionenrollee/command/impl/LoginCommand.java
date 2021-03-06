package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.service.SessionRequestContent;
import by.epam.receptionenrollee.validator.Validator;

public class LoginCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        return new Validator().getVerifiedUserPage(sessionRequestContent);
    }
}
