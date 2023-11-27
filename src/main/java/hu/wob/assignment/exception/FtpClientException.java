package hu.wob.assignment.exception;

public class FtpClientException extends BaseException {

    public FtpClientException(String message) {
        super(message);
    }

    public FtpClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpClientException(Throwable cause) {
        super(cause);
    }
}
