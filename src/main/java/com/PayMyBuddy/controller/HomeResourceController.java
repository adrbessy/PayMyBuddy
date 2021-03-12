package com.PayMyBuddy.controller;

import com.PayMyBuddy.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeResourceController {

  @Autowired
  private FriendService friendService;

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
