package com.example.waes.test.exceptions;

public abstract class BaseException extends RuntimeException {
    private String code;

    public BaseException(String code) {
        this.code = code;
    }

    protected String getCode() {
        return this.code;
    };
}
