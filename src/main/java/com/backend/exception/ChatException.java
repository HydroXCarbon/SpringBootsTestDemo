package com.backend.exception;

public class ChatException extends BaseException {

    public ChatException(String message) {
        super("chat." + message);
    }

    public static EmailException accessDenied() {
        return new EmailException("access.denied");
    }

}
