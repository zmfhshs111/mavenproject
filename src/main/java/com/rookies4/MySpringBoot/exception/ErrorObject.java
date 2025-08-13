package com.rookies4.MySpringBoot.exception;

public class ErrorObject {
    private String message;

    public ErrorObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
