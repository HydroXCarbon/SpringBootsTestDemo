package com.backend.exception;

public class EmailException extends BaseException {

    public EmailException(String message) {
        super("email." + message);
    }

    public static EmailException templateNotFound() {
        return new EmailException("template.not.found");
    }

}

