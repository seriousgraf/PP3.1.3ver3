package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "admin/all-users";
    }

    @GetMapping("/addNewUser")
    public String getUserForm(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        List<Role> roles = userService.findAllRoles();
        model.addAttribute("allRoles", roles);

        return "admin/new-user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult, Model model) {

        List<Role> roles = userService.findAllRoles();
        model.addAttribute("allRoles", roles);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());

            return "/admin/new-user";
        }

        if (userService.existsUserByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "user.email.exists",
                    "Пользователь с таким email уже существует");

            return "/admin/new-user";
        }

        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult, Model model) {

        List<Role> roles = userService.findAllRoles();
        model.addAttribute("allRoles", roles);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());

            return "/admin/update-user";
        }

        User existingUser = userService.findUserById(user.getId());
        if (!existingUser.getEmail().equals(user.getEmail())
                && userService.existsUserByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "user.email.exists",
                    "Пользователь с таким email уже существует");

            return "/admin/update-user";
        }

        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/updateInfo")
    public String initUserForm(@RequestParam("userId") int id, Model model) {

        User user = userService.getUser(id);
        model.addAttribute("user", user);

        List<Role> roles = userService.findAllRoles();
        model.addAttribute("allRoles", roles);

        return "/admin/update-user";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        userService.deleteUser(id);

        return "redirect:/admin/users";
    }

}