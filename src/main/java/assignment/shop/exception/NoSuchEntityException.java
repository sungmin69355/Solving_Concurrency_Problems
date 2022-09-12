package assignment.shop.exception;

public class NoSuchEntityException extends ApplicationException {

    public NoSuchEntityException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchEntityException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
