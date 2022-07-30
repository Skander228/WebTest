package com.test.Web.controllers;

import com.test.Web.models.Role;
import com.test.Web.models.User;
import com.test.Web.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password, Model model, User user) {

        User userFromDb = userRepository.findByName(username);
        User emailFromDb = userRepository.findByEmail(email);

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
        final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,20})$";

        if (userFromDb != null) {
            model.addAttribute("error_name", "Такое имя уже занято");
            return "registration";
        } else if (username.length() >= 20 || username.length() <= 4) {
            model.addAttribute("name_error", "Имя не соотведствует условию");
            return "registration";
        } else if (emailFromDb != null) {
            model.addAttribute("error_email", "Такая email уже занят");
            return "registration";
        } else if (!password.matches(PASSWORD_PATTERN)){
            model.addAttribute("password_error", "Пароль не соотведствует условию");
            return "registration";
        } else if (!email.matches(EMAIL_REGEX)) {
            model.addAttribute("email_error", "Email не соотведствует условию");
            return "registration";
        } else {
            user = new User(username, email, password);
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
        }

        return "redirect:/login";
    }
}
