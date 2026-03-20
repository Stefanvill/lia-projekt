package se.iths.stefan.liaprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.stefan.liaprojekt.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
