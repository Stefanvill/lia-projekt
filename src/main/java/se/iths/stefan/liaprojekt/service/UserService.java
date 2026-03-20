package se.iths.stefan.liaprojekt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.stefan.liaprojekt.model.User;
import se.iths.stefan.liaprojekt.repository.UserRepository;


import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Spring Security: Ladda user för login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }


    // Skapa ny user (med BCrypt)
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Kontrollera om username finns
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Hämta alla users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Hämta user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Hämta user by UID
    public Optional<User> getUserByUid(Long uid) {
        return userRepository.findById(uid);
    }

    // Uppdatera user
    public User updateUser(User user) {
        Optional<User> existing = userRepository.findById(user.getUid());
        if (existing.isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    // Radera user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
