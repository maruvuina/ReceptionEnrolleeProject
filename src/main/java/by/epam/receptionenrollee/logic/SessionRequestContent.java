package by.epam.receptionenrollee.logic;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static by.epam.receptionenrollee.command.RequestParam.*;

public class SessionRequestContent {
    private static final Logger logger = LogManager.getLogger(SessionRequestContent.class);
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private boolean isSessionInvalidate = false;
    private HttpServletRequest request;
    private String userLocale;

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setRequestAttribute(String attributeName, Object value) {
        requestAttributes.put(attributeName, value);
    }

    public void setSessionAttribute(String sessionAttributeName, Object value) {
        sessionAttributes.put(sessionAttributeName, value);
    }

    public SessionRequestContent(HttpServletRequest request) {
        this.request = request;
        requestAttributes = Collections.list(request.getAttributeNames())
                .stream()
                .collect(Collectors.toMap(attributeName -> attributeName, request::getAttribute));
        requestParameters = Collections.list(request.getParameterNames())
                .stream()
                .collect(Collectors.toMap(parameterName -> parameterName, request::getParameterValues));
        sessionAttributes = Collections.list(request.getSession().getAttributeNames())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(sessionName -> sessionName, request.getSession()::getAttribute));
        userLocale = (String) request.getSession().getAttribute(PARAM_NAME_LANGUAGE_SITE);
    }

    public String getParameter(String parameterName) {
        String parameter = null;
        for (Map.Entry<String, String[]> entryParameter : requestParameters.entrySet()) {
            if (entryParameter.getKey().equals(parameterName)) {
                parameter = String.join("", entryParameter.getValue());
            }
        }
//        String []parameterValues =
//                requestParameters
//                        .entrySet()
//                        .stream()
//                        .filter(e -> e.getKey().equals(parameterName))
//                        .map(Map.Entry::getValue)
//                        .findFirst()
//                        .orElse(null);
//        return String.join("", parameterValues);
        return parameter;
    }

    public String getSessionParameter(String sessionParameterName) {
       return String.valueOf(sessionAttributes
                .entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sessionParameterName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null));

    }

    public void updateRequestSession(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        requestParameters.forEach(request::setAttribute);
        sessionAttributes.forEach(request::setAttribute);
        if(isSessionInvalidate) {
            request.getSession().invalidate();
        }
    }

    public Part getPart() {
        Part part = null;
        try {
            part = request.getPart(PARAM_NAME_FILE);
        } catch (IOException | ServletException e) {
            logger.log(Level.ERROR, "Error while try to get part: ", e);
        }
        return part;
    }

    public String getUserLocale() {
        return userLocale;
    }

    public void setSessionInvalidate(boolean sessionInvalidate) {
        isSessionInvalidate = sessionInvalidate;
    }

    public boolean isSessionInvalidate() {
        return isSessionInvalidate;
    }

    public List<String> getParametersByName(String paratersName) {
        return Arrays.asList(request.getParameterValues(paratersName));
    }
}
