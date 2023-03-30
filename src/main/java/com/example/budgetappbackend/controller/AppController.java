package com.example.budgetappbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class AppController {

    @RequestMapping(value = { "", "/", "/addexpense", "/expenselist" })
    public String homePage(Map<String, Object> model) {
        return "index.html";
    }


}
