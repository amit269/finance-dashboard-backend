package com.finance.dashboard.user.exception;

import com.finance.dashboard.user.constant.ErrorCodeEnum;
import com.finance.dashboard.user.dto.UserErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserErrorResponse> handleUserException(UserException ex){
        UserErrorResponse response = new UserErrorResponse(ex.getErrorCode(),ex.getErrorMessage());
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserErrorResponse> handleGenericException(){
        UserErrorResponse response = new UserErrorResponse(ErrorCodeEnum.GENERIC_ERROR.getCode(),
                ErrorCodeEnum.GENERIC_ERROR.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

