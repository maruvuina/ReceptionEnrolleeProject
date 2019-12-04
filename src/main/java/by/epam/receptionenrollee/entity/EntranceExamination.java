package by.epam.receptionenrollee.entity;

public class EntranceExamination extends Entity {
    private int idEnrollee;
    private int languageMark;
    private int firstProfileExamMark;
    private int secondProfileExamMark;

    public EntranceExamination() {}

    public EntranceExamination(int idEntranceExamination, int idEnrollee,
                               int languageMark,
                               int firstProfileExamMark,
                               int secondProfileExamMark) {
        super(idEntranceExamination);
        this.idEnrollee = idEnrollee;
        this.languageMark = languageMark;
        this.firstProfileExamMark = firstProfileExamMark;
        this.secondProfileExamMark = secondProfileExamMark;
    }

    public int getIdEnrollee() {
        return idEnrollee;
    }

    public void setIdEnrollee(int idEnrollee) {
        this.idEnrollee = idEnrollee;
    }

    public int getLanguageMark() {
        return languageMark;
    }

    public void setLanguageMark(int languageMark) {
        this.languageMark = languageMark;
    }

    public int getFirstProfileExamMark() {
        return firstProfileExamMark;
    }

    public void setFirstProfileExamMark(int firstProfileExamMark) {
        this.firstProfileExamMark = firstProfileExamMark;
    }

    public int getSecondProfileExamMark() {
        return secondProfileExamMark;
    }

    public void setSecondProfileExamMark(int secondProfileExamMark) {
        this.secondProfileExamMark = secondProfileExamMark;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EntranceExamination{");
        sb.append("idEnrollee=").append(idEnrollee);
        sb.append(", languageMark=").append(languageMark);
        sb.append(", firstProfileExamMark=").append(firstProfileExamMark);
        sb.append(", secondProfileExamMark=").append(secondProfileExamMark);
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
        EntranceExamination that = (EntranceExamination) o;
        if (getIdEnrollee() != that.getIdEnrollee()) {
            return false;
        }
        if (getLanguageMark() != that.getLanguageMark()) {
            return false;
        }
        if (getFirstProfileExamMark() != that.getFirstProfileExamMark()) {
            return false;
        }
        return getSecondProfileExamMark() == that.getSecondProfileExamMark();
    }

    @Override
    public int hashCode() {
        int result = idEnrollee;
        result = 31 * result + languageMark;
        result = 17 * result + firstProfileExamMark;
        result = 31 * result + secondProfileExamMark;
        return result;
    }
}
