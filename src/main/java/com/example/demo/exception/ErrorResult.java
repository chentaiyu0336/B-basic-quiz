package com.example.demo.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResult {
    private Date timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResult(Date timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
