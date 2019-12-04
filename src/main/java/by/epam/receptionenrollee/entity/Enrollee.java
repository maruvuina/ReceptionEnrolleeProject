package by.epam.receptionenrollee.entity;

public class Enrollee extends Entity {
    private int idUser;
    private int idSpeciality;
    private String birthday;
    private String district;
    private String locality;
    private String avatar;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Enrollee{");
        sb.append("idUser=").append(idUser);
        sb.append(", idSpeciality=").append(idSpeciality);
        sb.append(", birthday='").append(birthday).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", locality='").append(locality).append('\'');
        sb.append(", avatar='").append(avatar);
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
        if (getIdUser() != enrollee.getIdUser()) {
            return false;
        }
        if (getIdSpeciality() != enrollee.getIdSpeciality()) {
            return false;
        }
        if (getBirthday() != null ? !getBirthday().equals(enrollee.getBirthday()) : enrollee.getBirthday() != null) {
            return false;
        }
        if (getDistrict() != null ? !getDistrict().equals(enrollee.getDistrict()) : enrollee.getDistrict() != null) {
            return false;
        }
        if (getLocality() != null ? !getLocality().equals(enrollee.getLocality()) : enrollee.getLocality() != null) {
            return false;
        }
        return getAvatar() != null ? getAvatar().equals(enrollee.getAvatar()) : enrollee.getAvatar() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getIdUser();
        result = 17 * result + getIdSpeciality();
        result = 31 * result + (getBirthday() != null ? getBirthday().hashCode() : 0);
        result = 17 * result + (getDistrict() != null ? getDistrict().hashCode() : 0);
        result = 31 * result + (getLocality() != null ? getLocality().hashCode() : 0);
        result = 17 * result + (getAvatar() != null ? getAvatar().hashCode() : 0);
        return result;
    }
}
