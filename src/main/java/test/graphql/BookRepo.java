package test.graphql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepo extends JpaRepository<Book, String> {

    @Query(value = "SELECT b.* FROM book b inner join book_author ba on ba.book_id = b.id and ba.author_id = :authorId", nativeQuery = true)
    List<Book> findByAuthorId(@Param("authorId") String authorId);

}
