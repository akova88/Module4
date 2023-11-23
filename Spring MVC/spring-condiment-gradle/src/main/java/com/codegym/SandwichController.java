package com.codegym;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SandwichController {

    @GetMapping("/sandwich")
    public String home(ModelMap model, @RequestParam(name = "condiment", required = false) String[] condiments) {

        model.addAttribute("condiments", condiments);
        return "home";
    }
}
