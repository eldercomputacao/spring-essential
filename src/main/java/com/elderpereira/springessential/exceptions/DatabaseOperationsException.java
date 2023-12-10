package com.elderpereira.springessential.exceptions;

public class DatabaseOperationsException extends RuntimeException {
  public DatabaseOperationsException(String message) {
    super(message);
  }
}
