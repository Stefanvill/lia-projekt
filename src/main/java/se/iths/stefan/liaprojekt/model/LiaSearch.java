package se.iths.stefan.liaprojekt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LiaPost {
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
