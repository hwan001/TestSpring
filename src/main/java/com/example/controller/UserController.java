package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/profile")
    public String showInventoryPage(Model model) {
        model.addAttribute("pageTitle", "프로필");
        model.addAttribute("bodyView", "/WEB-INF/views/user/profile.jsp");
        return "common/layout";
    }
}
