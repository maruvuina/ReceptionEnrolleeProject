package by.epam.receptionenrollee.entity;


public class SchoolMark extends Entity {
    private int idSubject;
    private int idEmplloee;
    private int mark;

    public SchoolMark() {}

    public SchoolMark(int idSchoolMark, int idEmplloee, int idSubject, int mark) {
        super(idSchoolMark);
        this.idEmplloee = idEmplloee;
        this.idSubject = idSubject;
        this.mark = mark;
    }

    public int getIdEmplloee() {
        return idEmplloee;
    }

    public void setIdEmplloee(int idEmplloee) {
        this.idEmplloee = idEmplloee;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchoolMark{");
        sb.append("idSubject=").append(idSubject);
        sb.append(", idEmplloee=").append(idEmplloee);
        sb.append(", mark=").append(mark);
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
        SchoolMark that = (SchoolMark) o;
        if (getIdSubject() != that.getIdSubject()) {
            return false;
        }
        if (getIdEmplloee() != that.getIdEmplloee()) {
            return false;
        }
        return getMark() == that.getMark();
    }

    @Override
    public int hashCode() {
        int result = idEmplloee;
        result = 31 * result + idSubject;
        result = 17 * result + mark;
        return result;
    }
}
