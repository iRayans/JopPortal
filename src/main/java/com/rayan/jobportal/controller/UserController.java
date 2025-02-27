package com.rayan.jobportal.controller;

import com.rayan.jobportal.entity.User;
import com.rayan.jobportal.entity.UserType;
import com.rayan.jobportal.service.UserService;
import com.rayan.jobportal.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserTypeService userTypeService;

    @Autowired
    public UserController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UserType> userTypes = userTypeService.getAll();
        model.addAttribute("getAllTypes", userTypes);
        model.addAttribute("user", new User());
        return "register";
    }
}
