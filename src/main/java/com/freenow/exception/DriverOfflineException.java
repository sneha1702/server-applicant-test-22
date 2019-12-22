package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Offline drivers cannot select Car!")
public class DriverOfflineException extends Exception {
  static final long serialVersionUID = -786850205629054920L;

  public DriverOfflineException(String message) {
    super(message);
  }
}
