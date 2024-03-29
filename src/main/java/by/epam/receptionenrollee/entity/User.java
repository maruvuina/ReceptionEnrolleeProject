package by.epam.receptionenrollee.entity;


public class User extends Entity {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String password;
    private RoleEnum roleEnum;

    public User() {
        super();
    }

    public User(int id, String firstName, String lastName, String middleName,
                String email, String password, RoleEnum roleEnum) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
        this.roleEnum = roleEnum;
    }

    public User(String firstName, String lastName, String middleName,
                String email, String password, RoleEnum roleEnum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
        this.roleEnum = roleEnum;
    }

    public User(String email, String password, RoleEnum roleEnum) {
        this.email = email;
        this.password = password;
        this.roleEnum = roleEnum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id='").append(getId()).append('\'');
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", middleName='").append(middleName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", roleEnum=").append(roleEnum);
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
        User user = (User) o;
        return (firstName == user.firstName || firstName != null && firstName.equals(user.firstName)) &&
                (lastName == user.lastName || lastName != null && lastName.equals(user.lastName)) &&
                (middleName == user.middleName || middleName != null && middleName.equals(user.middleName)) &&
                (email == user.email || email != null && email.equals(user.email)) &&
                (password == user.password || password != null && password.equals(user.password)) &&
                (roleEnum == user.roleEnum || roleEnum != null && roleEnum.equals(user.roleEnum));
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 17 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 17 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 17 * result + (roleEnum != null ? roleEnum.hashCode() : 0);
        return result;
    }
}
