package by.epam.receptionenrollee.filter;
import by.epam.receptionenrollee.command.PagePath;
import by.epam.receptionenrollee.entity.RoleEnum;
import by.epam.receptionenrollee.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(dispatcherTypes = { DispatcherType.FORWARD },
        filterName = "adminForward",
        urlPatterns = { "/jsp/app/admin/*" } )
public class AdminForwardFilter implements Filter {
    @Override
    public void init(FilterConfig fConfig) throws ServletException  {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException  {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        User user = (User) httpRequest.getSession().getAttribute(PagePath.USER);
        if(user != null && !user.getRoleEnum().equals(RoleEnum.ADMIN))  {
            RequestDispatcher dispatcher =
                    request.getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy()  {}
}
