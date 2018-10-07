package ru.chagay.thymeleaf.controller;

import ru.chagay.thymeleaf.form.UserForm;
import ru.chagay.thymeleaf.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    private static List<User> users = new ArrayList<User>();

    static {
        users.add(new User("Bill", "Gates"));
        users.add(new User("Steve", "Jobs"));
    }

    // ​​​​​​​
    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);
        model.addAttribute("users", users);

        return "index";
    }

    @RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
    public String showAddUserPage(Model model) {

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "newuser";
    }

    @RequestMapping(value = { "/adduser" }, method = RequestMethod.POST)
    public String saveUser(Model model,
                             @ModelAttribute("userForm") UserForm userForm) {

        String login = userForm.getLogin();
        String password = userForm.getPassword();

        if (login != null && login.length() > 0 //
                && password != null && password.length() > 0) {
            User newPerson = new User(login, password);
            users.add(newPerson);

            return "redirect:/index";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "newuser";
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String showLoginUserPage(Model model) {
        return "login";
    }
}