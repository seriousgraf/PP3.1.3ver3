package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.UserUserDetailsImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public String showUser(Model model, Principal principal) {
        UserUserDetailsImpl user = (UserUserDetailsImpl) ((Authentication) principal).getPrincipal();
        List<User> users = Collections.singletonList(userService.getUser(user.getId()));
        model.addAttribute("user", users);
        return "/user/user";
    }
}
