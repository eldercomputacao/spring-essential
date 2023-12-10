package com.elderpereira.springessential.handler;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError implements Serializable {
  @Serial
  private static final long serialVersionUID = -2688847009383216312L;
  
  private String message;
  private String details;
  private int status;
  private LocalDateTime timestamp;
}
