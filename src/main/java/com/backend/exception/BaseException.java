package com.backend.exception;

import java.io.IOException;

public abstract class BaseException extends IOException {

    public BaseException(String message) {
        super(message);
    }
}
