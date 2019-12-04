package by.epam.receptionenrollee.exception;

public class CommnadException extends Exception {
    public CommnadException() {
        super();
    }

    public CommnadException(String cause) {
        super(cause);
    }

    public CommnadException(Throwable t) {
        super(t);
    }

    public CommnadException(String cause, Throwable t) {
        super(cause, t);
    }
}
