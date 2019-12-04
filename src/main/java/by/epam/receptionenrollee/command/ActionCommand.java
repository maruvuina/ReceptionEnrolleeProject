package by.epam.receptionenrollee.command;

import by.epam.receptionenrollee.logic.SessionRequestContent;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
    //TODO возращает Router и принимает SessionRequestContent
    String execute(SessionRequestContent sessionRequestContent);
}
