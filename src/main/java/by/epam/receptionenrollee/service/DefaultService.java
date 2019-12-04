package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.resource.ConfigurationManager;

public final class DefaultService {
    private String path = ConfigurationManager.getProperty("path.page.login");
    public String getPath(){
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
