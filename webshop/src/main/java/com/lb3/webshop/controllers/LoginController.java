package com.lb3.webshop.controllers;

import com.lb3.webshop.services.LoginService;
import com.lb3.webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.lb3.webshop.repsonses.login.LoginRedirect;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(String username, String password) {
        if(this.loginService.handleLogin(username, password)){
            return "redirect:/home";
        }
        return "/home";
    }
}