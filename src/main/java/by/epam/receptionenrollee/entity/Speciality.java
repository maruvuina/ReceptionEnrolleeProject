package by.epam.receptionenrollee.entity;


public class Speciality extends Entity {
    private String specialityName;
    private int plan;
    private int idDepartment;

    public Speciality() {}

    public Speciality(int idSpeciality,
                      String specialityName,
                      int plan,
                      int idDepartment) {
        super(idSpeciality);
        this.specialityName = specialityName;
        this.plan = plan;
        this.idDepartment = idDepartment;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Speciality{");
        sb.append("specialityName='").append(specialityName).append('\'');
        sb.append(", plan=").append(plan);
        sb.append(", idDepartment=").append(idDepartment);
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
        Speciality that = (Speciality) o;
        if (getPlan() != that.getPlan()) {
            return false;
        }
        if (getIdDepartment() != that.getIdDepartment()) {
            return false;
        }
        return getSpecialityName() != null ? getSpecialityName().equals(that.getSpecialityName()) : that.getSpecialityName() == null;
    }

    @Override
    public int hashCode() {
        int result = specialityName.hashCode();
        result = 31 * result + plan;
        result = 17 * result + idDepartment;
        return result;
    }
}