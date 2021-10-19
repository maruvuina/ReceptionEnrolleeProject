package by.epam.receptionenrollee.entity;


public class Faculty extends Entity {
    private String facultyName;
    private String firstExam;
    private String secondExam;
    private String thirdExam;

    public Faculty() {}

    public Faculty(int idFaculty, String facultyName,
                   String firstExam,
                   String secondExam,
                   String thirdExam) {
        super(idFaculty);
        this.facultyName = facultyName;
        this.firstExam = firstExam;
        this.secondExam= secondExam;
        this.thirdExam = thirdExam;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFirstExam() {
        return firstExam;
    }

    public void setFirstExam(String firstExam) {
        this.firstExam = firstExam;
    }

    public String getSecondExam() {
        return secondExam;
    }

    public void setSecondExam(String secondExam) {
        this.secondExam = secondExam;
    }

    public String getThirdExam() {
        return thirdExam;
    }

    public void setThirdExam(String thirdExam) {
        this.thirdExam = thirdExam;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Faculty{");
        sb.append("facultyName='").append(facultyName).append('\'');
        sb.append(", firstExam='").append(firstExam).append('\'');
        sb.append(", secondExam='").append(secondExam).append('\'');
        sb.append(", thridExam='").append(thirdExam).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Faculty faculty = (Faculty) o;
        return (facultyName == faculty.facultyName || facultyName != null && facultyName.equals(faculty.facultyName)) &&
                (firstExam == faculty.firstExam || firstExam != null && firstExam.equals(faculty.firstExam)) &&
                (secondExam == faculty.secondExam || secondExam != null && secondExam.equals(faculty.secondExam)) &&
                (thirdExam == faculty.thirdExam || thirdExam != null && thirdExam.equals(faculty.thirdExam));
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (facultyName != null ? facultyName.hashCode() : 0);
        result = 17 * result + (firstExam != null ? firstExam.hashCode() : 0);
        result = 31 * result + (secondExam != null ? secondExam.hashCode() : 0);
        result = 17 * result + (thirdExam != null ? thirdExam.hashCode() : 0);
        return result;
    }
}
