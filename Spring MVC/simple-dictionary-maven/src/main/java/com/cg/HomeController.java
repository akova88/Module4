package com.cg;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/dictionary")
    public String search(@RequestParam("search") String search, Model model) {
        Map<String, String> dic = new HashMap<>();
        dic.put("hello", "Xin chào");
        dic.put("how", "Thế nào");
        dic.put("book", "Quyển vở");
        dic.put("computer", "Máy tính");

        String result = dic.get(search);
        if (result != null) {
            model.addAttribute("result", result);
        } else {
            result = "Không tìm thấy";
            model.addAttribute("result", result);
        }
        return "dictionary";
    }
}
