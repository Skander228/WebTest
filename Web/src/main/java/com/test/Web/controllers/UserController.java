package com.test.Web.controllers;

import com.test.Web.models.Role;
import com.test.Web.models.User;
import com.test.Web.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        Iterable<Role> roles = List.of(Role.values());
        model.addAttribute("roles", roles);
        return "userEdit";
    }

    @PostMapping("{user}")
    public String userSave(@RequestParam String name,
                           @RequestParam("userID") User user,
                           @RequestParam Map<String, String> form, Model model) {
        user.setName(name);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));

            }
        }

        userRepository.save(user);

        return "redirect:/user";
    }
}
