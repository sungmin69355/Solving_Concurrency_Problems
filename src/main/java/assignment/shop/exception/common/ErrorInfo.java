package assignment.shop.exception.common;

import assignment.shop.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ErrorInfo {
    private final String code;
    private final String message;

    public static ErrorInfo info(ErrorCode errorCode) {
        return new ErrorInfo(errorCode.getCode(), errorCode.getMessage());
    }
}
