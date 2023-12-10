package com.elderpereira.springessential.exceptions;

public class BusinessException extends RuntimeException {
  public BusinessException(String message) {
    super(message);
  }
}
