package hu.wob.assignment.exception;

public class DataMappingException extends BaseException {

    public DataMappingException(String message) {
        super(message);
    }

    public DataMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataMappingException(Throwable cause) {
        super(cause);
    }
}
