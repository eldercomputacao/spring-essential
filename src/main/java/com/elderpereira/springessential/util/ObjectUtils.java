package com.elderpereira.springessential.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectUtils {
  public static String parseToJsonString(Object object) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      return mapper.writeValueAsString(object);
    } catch (Exception ex) {
      return "";
    }
  }
}
