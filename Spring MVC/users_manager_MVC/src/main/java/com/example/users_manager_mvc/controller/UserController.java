package com.example.users_manager_mvc.controller;

import com.example.users_manager_mvc.dao.IUserDAO;
import com.example.users_manager_mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private IUserDAO userDAO;
    @GetMapping("/users")
    public String showList(Model model) {
        List<User> listUser = userDAO.getAllUsers();
        model.addAttribute("listUser", listUser);
        return "list";
    }
}
