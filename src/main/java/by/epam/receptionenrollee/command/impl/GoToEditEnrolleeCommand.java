package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.service.CommunicationService;
import by.epam.receptionenrollee.service.SessionRequestContent;

public class GoToEditEnrolleeCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
       return new CommunicationService().setDataBeforeGoToEdit(sessionRequestContent);
    }
}
