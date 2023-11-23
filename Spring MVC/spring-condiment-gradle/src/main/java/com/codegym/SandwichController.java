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

    @GetMapping("/calculator")
    public String calculator(ModelMap model, @RequestParam(name = "num1", required = false) Integer num1,
                                             @RequestParam(name = "num2", required = false) Integer num2,
                             @RequestParam(name = "operation", required = false) String operation) {
        int result = 0;

        if (num1 == null || num2 == null || operation == null) {
            model.addAttribute("error", "Invalid input");
            return "calculator";
        }
        switch (operation) {
            case "Addition(+)":
                result = num1 + num2;
                break;
            case "Subtraction(-)":
                result = num1 - num2;
                break;
            case "Multiplication(x)":
                result = num1 * num2;
                break;
            case "Division(/)":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    model.addAttribute("error", "Cannot divide by zero");
                    return "calculator";
                }
                break;
            default:
                model.addAttribute("error", "Invalid operation");
                return "calculator";
        }
        model.addAttribute("result", result);

        return "calculator";
    }
}
