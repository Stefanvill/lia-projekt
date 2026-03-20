package se.iths.stefan.liaprojekt.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.iths.stefan.liaprojekt.model.User;
import se.iths.stefan.liaprojekt.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Visa login-sidan
    @GetMapping("/login")
    public String login(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/lia";
        }
        model.addAttribute("loginError", "Fel användarnamn eller lösenord");
        return "login";
    }

    // Visa registreringssidan
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "lia/register";
    }

    // Hantera registrering
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user,
                           BindingResult result,
                           Model model) {

        if (result.hasErrors()) {
            return "lia/register";
        }

        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("error", "Användarnamnet finns redan");
            return "lia/register";
        }

        userService.createUser(user);
        return "redirect:/login?registered=true";
    }
}
