package test.graphql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepo extends JpaRepository<Author, String> {

    Author findByName(String name);

}
