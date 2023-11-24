package com.codegym.controller;

import com.codegym.model.EmailConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class EmailController {
    EmailConfig emailConfig = new EmailConfig();
    List<Integer> sizes = new ArrayList<>(Arrays.asList(5, 10, 15, 25, 50, 100));
    @GetMapping("/")
    public String show(Model model) {
        model.addAttribute("emailConfig", emailConfig);
        model.addAttribute("sizes", sizes);
        return "/email";
    }

   @PostMapping("/")
    public String update(EmailConfig emailConfig) {
        this.emailConfig.setLanguage(emailConfig.getLanguage());
        this.emailConfig.setPageSize(emailConfig.getPageSize());
        this.emailConfig.setSpam(emailConfig.isSpam());
        this.emailConfig.setSignature(emailConfig.getSignature());
        return "redirect:/";
   }
}
