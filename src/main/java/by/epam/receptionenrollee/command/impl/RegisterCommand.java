package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.validator.Validator;

import static by.epam.receptionenrollee.command.RequestParam.PARAM_NAME_LOGIN_FROM_COMPITITION_REGISTER;

public class RegisterCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        Validator validator = new Validator();
        String page;
        page = validator.getCompititionRegisterPage(sessionRequestContent);
        return page;
    }
}
