package com.PayMyBuddy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NonexistentException extends RuntimeException {

  public NonexistentException(String message) {
    super(message);
  }

}