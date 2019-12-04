package by.epam.receptionenrollee.controller;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.command.ActionFactory;
import by.epam.receptionenrollee.dao.pool.CustomConnectionPool;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.resource.ConfigurationManager;
import by.epam.receptionenrollee.resource.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static by.epam.receptionenrollee.command.PagePath.INDEX;
import static by.epam.receptionenrollee.command.RequestParam.*;

@WebServlet(name = "Controller", urlPatterns = {"/controller"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        String page;
        ActionFactory client = ActionFactory.getInstance();
        Optional<ActionCommand> commandOptional =
                client.defineCommand(request.getParameter("command"));
        ActionCommand command = commandOptional
                .orElse(
                        v -> {
                            request.setAttribute("wrongAction",
                                    request.getParameter("command")
                            + new MessageManager((String) request
                                            .getSession()
                                            .getAttribute(PARAM_NAME_LANGUAGE_SITE)).getProperty("message.wrongaction"));
                                return null;
                        });
        SessionRequestContent sessionRequestContent = new SessionRequestContent(request);
        page = command.execute(sessionRequestContent);
        System.out.println("LOGIN----->" + sessionRequestContent.getParameter("login"));
        System.out.println("PAGE------> "+ page);
        //System.out.println(" " + sessionRequestContent.getParameter("enrollee-faculty"));
        System.out.println("PARAM_NAME_ENROLLEES_INFORMATION_MAP " + sessionRequestContent.getParameter(PARAM_NAME_ENROLLEES_INFORMATION_MAP));
        System.out.println("PARAM_NAME_LOGIN_FROM_COMPITITION_REGISTER " + sessionRequestContent.getSessionParameter(PARAM_NAME_LOGIN_FROM_COMPITITION_REGISTER));
        sessionRequestContent.updateRequestSession(request);


        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager.getProperty(INDEX);
            request.getSession().setAttribute("nullPage",
                    new MessageManager(sessionRequestContent.getUserLocale()).getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }

    @Override
    public void destroy() {
        CustomConnectionPool.INSTANCE.destroyPool();
    }
}
