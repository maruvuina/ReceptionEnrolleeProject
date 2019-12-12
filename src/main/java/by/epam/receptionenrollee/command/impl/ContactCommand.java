package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.validator.Validator;

public class ContactCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        return new Validator().getPageAfterSendingMessageToEnrollee(sessionRequestContent);
    }
}
