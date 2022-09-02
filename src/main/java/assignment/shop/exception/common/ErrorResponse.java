package assignment.shop.exception.common;


import assignment.shop.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ErrorResponse {

    private final ErrorInfo errorInfo;
    private final List<RequestFieldError> fieldErrors;

    public static ErrorResponse of(MethodArgumentNotValidException e) {
        List<RequestFieldError> errors = ErrorResponse.RequestFieldError.of(e.getBindingResult());
        return new ErrorResponse(ErrorInfo.info(ErrorCode.INVALID_INPUT_VALUE), errors);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        String value = Optional.ofNullable(e.getValue())
                .map(Object::toString)
                .orElse("");

        List<ErrorResponse.RequestFieldError> errors =
                ErrorResponse.RequestFieldError.of(e.getName(), value, e.getErrorCode());

        return new ErrorResponse(ErrorInfo.info(ErrorCode.INVALID_INPUT_VALUE), errors);
    }

    public static ErrorResponse of(ErrorInfo errorInfo) {
        return new ErrorResponse(errorInfo, Collections.emptyList());
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RequestFieldError {

        private final String field;
        private final String value;
        private final String reason;

        private RequestFieldError(FieldError fieldError) {
            this.field = fieldError.getField();
            this.value = fieldError.getRejectedValue() == null ? "" : String.valueOf(fieldError.getRejectedValue());
            this.reason = fieldError.getDefaultMessage();
        }

        private static List<RequestFieldError> of(BindingResult bindingResult) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(RequestFieldError::new)
                    .collect(Collectors.toList());
        }

        private static List<RequestFieldError> of(String field, String value, String reason) {
            List<RequestFieldError> requestFieldErrors = new ArrayList<>();
            requestFieldErrors.add(new RequestFieldError(field, value, reason));
            return requestFieldErrors;
        }
    }
}
