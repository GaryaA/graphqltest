package test.graphql;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
public class Author {
    @Id
    @Column
    private String id;
    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    @PrePersist
    public void setValues() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
