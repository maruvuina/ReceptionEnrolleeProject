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
        return plan == that.plan &&
                idDepartment == that.idDepartment &&
                (specialityName == that.specialityName || specialityName != null && specialityName.equals(that.specialityName));
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (specialityName != null ? specialityName.hashCode() : 0);
        result = 17 * result + plan;
        result = 31 * result + idDepartment;
        return result;
    }
}
