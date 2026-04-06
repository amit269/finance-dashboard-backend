package com.finance.dashboard.user.exception;


import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class UserException extends  RuntimeException{
private final String errorCode;
private final String errorMessage;
private final HttpStatus status;

public  UserException(String errorCode, String errorMessage, HttpStatus status){

    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.status = status;
}

}
