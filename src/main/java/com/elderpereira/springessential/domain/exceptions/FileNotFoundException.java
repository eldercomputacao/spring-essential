package com.elderpereira.springessential.domain.exceptions;

public class FileNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileNotFoundException(String ex) {
        super(ex);
    }

    public FileNotFoundException(String ex, Throwable cause) {
        super(ex, cause);
    }
}
