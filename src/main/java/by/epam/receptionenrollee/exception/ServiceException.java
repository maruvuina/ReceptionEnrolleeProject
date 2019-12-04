package by.epam.receptionenrollee.exception;

public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }

    public ServiceException(String cause) {
        super(cause);
    }

    public ServiceException(Throwable t) {
        super(t);
    }

    public ServiceException(String cause, Throwable t) {
        super(cause, t);
    }
}
