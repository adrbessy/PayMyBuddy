package com.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeResourceController {

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/user")
  public String user() {
    return "user";
  }

  @GetMapping("/admin")
  public String admin() {
    return "<h1>Welcome admin</h1>";
  }

}
