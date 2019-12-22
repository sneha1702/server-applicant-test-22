package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Driver not found!")
public class DriverNotFoundException extends Exception {
  static final long serialVersionUID = -2065039487065168589L;

  public DriverNotFoundException(String message) {
    super(message);
  }
}
