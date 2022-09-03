package assignment.shop.exception;

import assignment.shop.exception.common.ErrorInfo;
import assignment.shop.exception.common.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ExceptionAdviser {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException e) {

        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<ErrorResponse> methodArgumentTypeMismatchExceptionHandler(
            MethodArgumentTypeMismatchException e) {

        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }

    @ExceptionHandler(ApplicationException.class)
    private ResponseEntity<ErrorResponse> applicationExceptionHandler(ApplicationException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ErrorResponse.of(ErrorInfo.info(e.getErrorCode())));
    }
}
