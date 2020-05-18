package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.entity.Enrollee;
import by.epam.receptionenrollee.entity.User;

public class EditInformation {
    private Enrollee enrollee;
    private User user;
    private EducationInformation educationInformation;

    public Enrollee getEnrollee() {
        return enrollee;
    }

    public void setEnrollee(Enrollee enrollee) {
        this.enrollee = enrollee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EducationInformation getEducationInformation() {
        return educationInformation;
    }

    public void setEducationInformation(EducationInformation educationInformation) {
        this.educationInformation = educationInformation;
    }
}
