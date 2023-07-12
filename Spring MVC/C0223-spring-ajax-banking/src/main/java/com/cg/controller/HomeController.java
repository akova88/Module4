package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/dashboard")
    public String showHome() {
        return ("dashboard");
    }

}
