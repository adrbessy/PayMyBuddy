package com.PayMyBuddy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.FORBIDDEN)
public class IsForbiddenException extends RuntimeException {

  public IsForbiddenException(String message) {
    super(message);
  }

}
