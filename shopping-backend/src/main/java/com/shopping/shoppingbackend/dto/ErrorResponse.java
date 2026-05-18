package com.shopping.shoppingbackend.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {

    private String code;
    private String message;
    private Map<String, String> errors;
    private String path;
    private LocalDateTime timestamp;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String code, String message, String path) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String code, String message, Map<String, String> errors, String path) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
