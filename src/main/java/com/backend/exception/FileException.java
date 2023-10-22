package com.backend.exception;

public class FileException extends BaseException {

    public FileException(String message) {
        super("file." + message);
    }

    public static FileException fileNull() {
        return new FileException("null");
    }

    public static FileException fileMaxSize() {
        return new FileException("fileMaxSize");
    }

    public static FileException unsupported() {
        return new FileException("unsupported");
    }
}
