package com.elderpereira.springessential.handler;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ResponseFieldError extends ResponseError {
  private List<FieldError> fieldErrors;
  
  @Data
  @Builder
  public static class FieldError {
    private String field;
    private String error;
  }
}
