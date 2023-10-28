package com.backend.exception;

public class UserException extends BaseException {

    public UserException(String message) {
        super("user." + message);
    }

    public static UserException unauthorized() {
        return new UserException("unauthorized");
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

    // CREATE

    public static UserException createEmailNull() {
        return new UserException("create.email.null");
    }

    public static UserException createEmailDuplicate() {
        return new UserException("create.email.duplicate");
    }

    public static UserException createUsernameNull() {
        return new UserException("create.username.null");
    }

    public static UserException createUsernameDuplicate() {
        return new UserException("create.username.Duplicate");
    }

    public static UserException createPasswordNull() {
        return new UserException("create.password.null");
    }

    // LOGIN

    public static UserException loginFailEmailNotFound() {
        return new UserException("login.fail");
    }

    public static UserException loginFailPasswordIncorrect() {
        return new UserException("login.fail");
    }

    public static UserException loginFailEmailNull() {
        return new UserException("login.email.null");
    }

    public static UserException loginFailPasswordNull() {
        return new UserException("login.request.null");
    }

    // NOT FOUND

    public static UserException notFound() {
        return new UserException("user.not.found");
    }

}

