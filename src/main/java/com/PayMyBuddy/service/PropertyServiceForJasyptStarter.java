package com.PayMyBuddy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceForJasyptStarter {

  @Value("${encrypted.property}")
  private String property;

  public String getProperty() {
    return property;
  }

}
