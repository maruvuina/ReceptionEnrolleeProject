package by.epam.receptionenrollee.entity;


public class SchoolSubject extends Entity {
    private String subjectName;

    public SchoolSubject() {}

    public SchoolSubject(String subjectName) {
        this.subjectName = subjectName;
    }

    public SchoolSubject(int idSchoolSubject, String subjectName) {
        super(idSchoolSubject);
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchoolSubject{");
        sb.append("subjectName='").append(subjectName).append('\'');
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
        SchoolSubject that = (SchoolSubject) o;
        return getSubjectName() != null ? getSubjectName().equals(that.getSubjectName()) : that.getSubjectName() == null;
    }

    @Override
    public int hashCode() {
        return 31 * subjectName.hashCode();
    }
}