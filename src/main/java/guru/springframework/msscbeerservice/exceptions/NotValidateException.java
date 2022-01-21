package guru.springframework.msscbeerservice.exceptions;

public class NotValidateException extends Exception {
    public NotValidateException() {
    }

    public NotValidateException(String message) {
        super(message);
    }

    public NotValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidateException(Throwable cause) {
        super(cause);
    }
}
