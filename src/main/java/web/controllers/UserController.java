package web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.services.UserService;
import web.util.UserValidator;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userServiceImplementation;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userServiceImplementation, UserValidator userValidator) {
        this.userServiceImplementation = userServiceImplementation;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userServiceImplementation.findAll());
        return "user/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userServiceImplementation.findById(id));
        return "user/show";
    }

    @GetMapping("/new")
    public String newUser(Model model, User user) {
        model.addAttribute("user", user);
        return "user/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) return "user/new";
        userServiceImplementation.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userServiceImplementation.findById(id));
        return "user/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        userValidator.validateToUpdate(user, bindingResult);
        if (bindingResult.hasErrors()) return "user/edit";
        userServiceImplementation.update(id, user);
        return "redirect:/user";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userServiceImplementation.delete(id);
        return "redirect:/user";
    }
}
