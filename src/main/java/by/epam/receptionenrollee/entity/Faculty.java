package by.epam.receptionenrollee.entity;

public class Faculty extends Entity {
    private String facultyName;
    private String firstExam;
    private String secondExam;
    private String thridExam;

    public Faculty() {}

    public Faculty(int idFaculty, String facultyName,
                   String firstExam,
                   String secondExam,
                   String thridExam) {
        super(idFaculty);
        this.facultyName = facultyName;
        this.firstExam = firstExam;
        this.secondExam= secondExam;
        this.thridExam = thridExam;
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

    public String getThridExam() {
        return thridExam;
    }

    public void setThridExam(String thridExam) {
        this.thridExam = thridExam;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Faculty{");
        sb.append("facultyName='").append(facultyName).append('\'');
        sb.append(", firstExam='").append(firstExam).append('\'');
        sb.append(", secondExam='").append(secondExam).append('\'');
        sb.append(", thridExam='").append(thridExam).append('\'');
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
        if (getFacultyName() != null ? !getFacultyName().equals(faculty.getFacultyName()) : faculty.getFacultyName() != null) {
            return false;
        }
        if (getFirstExam() != null ? !getFirstExam().equals(faculty.getFirstExam()) : faculty.getFirstExam() != null) {
            return false;
        }
        if (getSecondExam() != null ? !getSecondExam().equals(faculty.getSecondExam()) : faculty.getSecondExam() != null) {
            return false;
        }
        return getThridExam() != null ? getThridExam().equals(faculty.getThridExam()) : faculty.getThridExam() == null;
    }

    @Override
    public int hashCode() {
        int result = facultyName.hashCode();
        result = 17 * result + firstExam.hashCode();
        result = 31 * result + secondExam.hashCode();
        result = 17 * result + thridExam.hashCode();
        return result;
    }
}