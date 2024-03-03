package com.bimbo.app.exceptions;

import lombok.Data;

@Data
public class ApiResponseException<T> {
    private String message;
    private T data;

    public ApiResponseException(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
