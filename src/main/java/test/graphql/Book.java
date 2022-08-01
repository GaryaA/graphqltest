package test.graphql;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Book {

    @Id
    @Column
    private String id;

    @Column
    private String title;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private List<Author> authors;

    @PrePersist
    public void setValues() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
