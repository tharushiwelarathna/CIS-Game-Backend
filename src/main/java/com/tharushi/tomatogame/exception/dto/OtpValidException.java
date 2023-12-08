package com.tharushi.tomatogame.exception.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;



@Getter
@Setter
@ToString
public class OtpValidException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;
    private int code;

    public OtpValidException(String message) {
        super(message);
        this.message = message;
    }

    public OtpValidException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public OtpValidException(int code, String message, Throwable cause) {
        super(cause);
        this.message = message;
        this.code = code;
    }

    public OtpValidException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public OtpValidException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
