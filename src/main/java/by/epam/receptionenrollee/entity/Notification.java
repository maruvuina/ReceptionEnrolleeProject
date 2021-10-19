package by.epam.receptionenrollee.entity;


public class Notification extends Entity {
    private int idEnrollee;
    private boolean enrolment;

    public Notification() {}

    public Notification(int idEnrollee, boolean enrolment) {
        this.idEnrollee = idEnrollee;
        this.enrolment = enrolment;
    }

    public Notification(int idNotification, int idEnrollee, boolean enrolment) {
        super(idNotification);
        this.idEnrollee = idEnrollee;
        this.enrolment = enrolment;
    }

    public int getIdEnrollee() {
        return idEnrollee;
    }

    public void setIdEnrollee(int idEnrollee) {
        this.idEnrollee = idEnrollee;
    }

    public boolean isEnrolment() {
        return enrolment;
    }

    public void setEnrolment(boolean enrolment) {
        this.enrolment = enrolment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Notification{");
        sb.append("idEnrollee=").append(idEnrollee);
        sb.append(", enrolment=").append(enrolment);
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
        Notification that = (Notification) o;
        return idEnrollee == that.idEnrollee &&
                enrolment == that.enrolment;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + idEnrollee;
        result = 17 * result + Boolean.valueOf(enrolment).hashCode();
        return result;
    }
}
