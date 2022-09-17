package assignment.shop.exception;

public class ItemException extends ApplicationException {
    public ItemException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ItemException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
