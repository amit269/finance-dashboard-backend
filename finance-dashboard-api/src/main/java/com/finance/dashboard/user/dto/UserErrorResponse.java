package com.finance.dashboard.user.dto;


import lombok.Data;

@Data
public class UserErrorResponse {
    private String errorCode;
    private String errorMessage;

    public UserErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
