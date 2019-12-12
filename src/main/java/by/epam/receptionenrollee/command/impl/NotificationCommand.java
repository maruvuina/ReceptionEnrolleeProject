package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.resource.ConfigurationManager;
import by.epam.receptionenrollee.validator.Validator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.receptionenrollee.command.PagePath.ADMIN;
import static by.epam.receptionenrollee.command.PagePath.NOTIFICATION_ENROLLEE;
import static by.epam.receptionenrollee.command.RequestParam.*;
import static by.epam.receptionenrollee.command.RequestParam.PARAM_NAME_ROLE;


public class NotificationCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        return new Validator().getPageToNotifyEnrollee(sessionRequestContent);
    }
}
