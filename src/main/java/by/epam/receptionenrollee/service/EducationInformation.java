package by.epam.receptionenrollee.service;

public class EducationInformation {
    private int idEnrollee;
    private String facultyName;
    private String specialityName;
    private int enrolleeScore;
    private int enrolleePosition;
    private int enrolleesCount;
    private int facultyPlan;
    private String enrolleeFirstName;
    private String enrolleeLastName;
    private String enrolleeEmail;
    private String avatarEnrollee;

    public EducationInformation() {}

    public EducationInformation(int idEnrollee, String facultyName, String specialityName, int enrolleeScore, int enrolleePosition, int enrolleesCount, int facultyPlan) {
        this.idEnrollee = idEnrollee;
        this.facultyName = facultyName;
        this.specialityName = specialityName;
        this.enrolleeScore = enrolleeScore;
        this.enrolleePosition = enrolleePosition;
        this.enrolleesCount = enrolleesCount;
        this.facultyPlan = facultyPlan;
    }

    public int getIdEnrollee() {
        return idEnrollee;
    }

    public void setIdEnrollee(int idEnrollee) {
        this.idEnrollee = idEnrollee;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public int getEnrolleeScore() {
        return enrolleeScore;
    }

    public void setEnrolleeScore(int enrolleeScore) {
        this.enrolleeScore = enrolleeScore;
    }

    public int getEnrolleePosition() {
        return enrolleePosition;
    }

    public void setEnrolleePosition(int enrolleePosition) {
        this.enrolleePosition = enrolleePosition;
    }

    public int getEnrolleesCount() {
        return enrolleesCount;
    }

    public void setEnrolleesCount(int enrolleesCount) {
        this.enrolleesCount = enrolleesCount;
    }

    public int getFacultyPlan() {
        return facultyPlan;
    }

    public void setFacultyPlan(int facultyPlan) {
        this.facultyPlan = facultyPlan;
    }

    public void setEnrolleeFirstName(String enrolleeFirstName) {
        this.enrolleeFirstName = enrolleeFirstName;
    }

    public void setEnrolleeLastName(String enrolleeLastName) {
        this.enrolleeLastName = enrolleeLastName;
    }

    public String getEnrolleeFirstName() {
        return enrolleeFirstName;
    }

    public String getEnrolleeLastName() {
        return enrolleeLastName;
    }

    public String getEnrolleeEmail() {
        return enrolleeEmail;
    }

    public void setEnrolleeEmail(String enrolleeEmail) {
        this.enrolleeEmail = enrolleeEmail;
    }

    public String getAvatarEnrollee() {
        return avatarEnrollee;
    }

    public void setAvatarEnrollee(String avatarEnrollee) {
        this.avatarEnrollee = avatarEnrollee;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EducationInformation{");
        sb.append("enrolleeScore=").append(enrolleeScore);
        sb.append(", enrolleePosition=").append(enrolleePosition);
        sb.append(", enrolleesCount=").append(enrolleesCount);
        sb.append(", facultyPlan=").append(facultyPlan);
        sb.append('}');
        return sb.toString();
    }
}
