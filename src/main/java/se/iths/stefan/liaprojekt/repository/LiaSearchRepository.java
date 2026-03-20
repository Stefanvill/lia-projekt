package se.iths.stefan.liaprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.stefan.liaprojekt.model.LiaSearch;

import java.util.List;
import java.util.Optional;

@Repository
public interface LiaSearchRepository extends JpaRepository<LiaSearch, Long> {
    List<LiaSearch> findByUserUid(Long userUid);

    Optional<LiaSearch> findByIdAndUserUid(Long id, Long userUid);
}
