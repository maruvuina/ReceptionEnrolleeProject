package by.epam.receptionenrollee.controller;

import by.epam.receptionenrollee.command.ActionCommand;
import by.epam.receptionenrollee.command.ActionFactory;
import by.epam.receptionenrollee.dao.pool.CustomConnectionPool;
import by.epam.receptionenrollee.service.SessionRequestContent;
import by.epam.receptionenrollee.manager.ConfigurationManager;
import by.epam.receptionenrollee.manager.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.epam.receptionenrollee.command.PagePath.INDEX;

@WebServlet(urlPatterns = {"/controller"})
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
        ActionCommand command = client.defineCommand(request);
        SessionRequestContent sessionRequestContent = new SessionRequestContent(request);
        page = command.execute(sessionRequestContent);
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
