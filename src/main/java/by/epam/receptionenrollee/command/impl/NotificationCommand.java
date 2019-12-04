package by.epam.receptionenrollee.command.impl;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.resource.ConfigurationManager;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.receptionenrollee.command.PagePath.ADMIN;


public class NotificationCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(NotificationCommand.class);

    @Override//todo
    public String execute(SessionRequestContent sessionRequestContent) {
        String status = sessionRequestContent.getParameter("status");
        return ConfigurationManager.getProperty(ADMIN);
    }
}
