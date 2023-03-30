package com.example.budgetappbackend.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping(value = "/error")
    public String handleError(Map<String, Object> model) {
        return "index.html";
    }

}
