package by.epam.receptionenrollee.exception;

public class TranslationException extends Exception {
    public TranslationException() {
        super();
    }

    public TranslationException(String cause) {
        super(cause);
    }

    public TranslationException(Throwable t) {
        super(t);
    }

    public TranslationException(String cause, Throwable t) {
        super(cause, t);
    }
}
