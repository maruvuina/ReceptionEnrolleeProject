package by.epam.receptionenrollee.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static by.epam.receptionenrollee.command.RequestParam.PARAM_NAME_LANGUAGE_SITE;
import static java.util.Objects.isNull;

@WebFilter(urlPatterns = {"/*"}, initParams = {
        @WebInitParam(name = "defaultLocale", value = "ru_RU"),
        @WebInitParam(name = "supportedLocales", value = "en_US,ru_RU,be_BY")
})
public class LocaleFilter implements Filter {
    private String defaultLocale;
    private List<String> supportedLocales;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
        supportedLocales = Arrays.asList(filterConfig.getInitParameter("supportedLocales").split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String locale = (String) session.getAttribute(PARAM_NAME_LANGUAGE_SITE);
        if (isNull(locale)) {
            String browserLocale = servletRequest.getLocale().getLanguage();
            if (supportedLocales.contains(browserLocale)) {
                session.setAttribute(PARAM_NAME_LANGUAGE_SITE, browserLocale);
            } else {
                session.setAttribute(PARAM_NAME_LANGUAGE_SITE, defaultLocale);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        defaultLocale = null;
    }
}
