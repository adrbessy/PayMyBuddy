package com.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeResourceController {

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/")
  public String home() {
    return "index";
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/user")
  public String user() {
    return "user";
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/admin")
  public String admin() {
    return "admin";
  }

}
