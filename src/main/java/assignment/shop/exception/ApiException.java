package assignment.shop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException{

    private HttpStatus status;
    private String statusCode;
    private String message;
    public ApiException(HttpStatus status, String statusCode, String message){
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }
}
