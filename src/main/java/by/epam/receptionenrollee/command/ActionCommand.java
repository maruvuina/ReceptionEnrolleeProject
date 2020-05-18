package by.epam.receptionenrollee.command;

import by.epam.receptionenrollee.service.SessionRequestContent;

public interface ActionCommand {
    String execute(SessionRequestContent sessionRequestContent);
}
