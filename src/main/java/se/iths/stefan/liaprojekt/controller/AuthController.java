package se.iths.stefan.liaprojekt.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import se.iths.stefan.liaprojekt.model.User;
import se.iths.stefan.liaprojekt.repository.UserRepository;

@Controller
public class UserController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public boolean usernameExists(String username) {
        return repository.findByUsername(username).isPresent();
    }
}
