package com.elderpereira.springessential.domain.exceptions;

public class DatabaseOperationsException extends RuntimeException {
    public DatabaseOperationsException(String message) {
        super(message);
    }
}
