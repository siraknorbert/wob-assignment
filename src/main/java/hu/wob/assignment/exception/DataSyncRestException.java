package hu.wob.assignment.exception;

public class DataSyncRestException extends BaseException {

    public DataSyncRestException(String message) {
        super(message);
    }

    public DataSyncRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSyncRestException(Throwable cause) {
        super(cause);
    }
}
