package by.epam.receptionenrollee.entity;


public class Enrollee extends Entity {
    private int idUser;
    private int idSpeciality;
    private String birthday;
    private String district;
    private String locality;
    private String avatar;
    private int attempt;

    public Enrollee(){}

    public Enrollee(int id, int idUser, int idSpeciality,
                    String birthday, String district, String locality, String avatar) {
        super(id);
        this.idUser = idUser;
        this.idSpeciality = idSpeciality;
        this.birthday = birthday;
        this.district = district;
        this.locality = locality;
        this.avatar = avatar;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(int idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Enrollee{");
        sb.append("idUser=").append(idUser);
        sb.append(", idSpeciality=").append(idSpeciality);
        sb.append(", birthday='").append(birthday).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", locality='").append(locality).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", attempt=").append(attempt);
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
        Enrollee enrollee = (Enrollee) o;
        return idUser == enrollee.idUser &&
                idSpeciality == enrollee.idSpeciality &&
                attempt == enrollee.attempt &&
                (birthday == enrollee.birthday || birthday != null && birthday.equals(enrollee.birthday)) &&
                (district == enrollee.district || district != null && district.equals(enrollee.district)) &&
                (locality == enrollee.locality || locality != null && locality.equals(enrollee.locality)) &&
                (avatar == enrollee.avatar || avatar != null && avatar.equals(enrollee.avatar));
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + idUser;
        result = 17 * result + idSpeciality;
        result = 31 * result + attempt;
        result = 17 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 17 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        return result;
    }
}
