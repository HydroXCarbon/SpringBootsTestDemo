package com.backend.exception;

public class UserException extends BaseException {

    public UserException(String message) {
        super("user." + message);
    }

    public static UserException emailNull() {
        return new UserException("register.email.null");
    }

    public static UserException requestNull() {
        return new UserException("register.request.null");
    }

    public static UserException usernameNull() {
        return new UserException("register.username.null");
    }

    public static UserException passwordNull() {
        return new UserException("register.password.null");
    }
}

