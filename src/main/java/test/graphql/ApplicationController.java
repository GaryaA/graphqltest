package test.graphql;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ApplicationController {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @MutationMapping
    public Book saveBook(@Argument String title, @Argument List<Author> authors) {
        Book book = new Book();
        book.setTitle(title);

        if (authors != null && !authors.isEmpty()) {
            List<Author> dbAuthors = authorRepo.findAllById(authors.stream().map(Author::getId).collect(Collectors.toList()));
            dbAuthors.forEach(b -> b.getBooks().add(book));
            book.setAuthors(dbAuthors);
        }

        return bookRepo.save(book);
    }

    @MutationMapping
    public Author saveAuthor(@Argument String name, @Argument List<Book> books) {
        Author author = new Author();
        author.setName(name);

        if (books != null && !books.isEmpty()) {
            List<Book> mergedBooks = books.stream().filter(b -> Strings.isEmpty(b.getId())).collect(Collectors.toList());
            mergedBooks.addAll(
                    bookRepo.findAllById(books.stream().map(Book::getId).filter(Strings::isNotEmpty).collect(Collectors.toList()))
            );
            author.setBooks(mergedBooks);
        }

        return authorRepo.save(author);
    }

    @QueryMapping
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @QueryMapping
    public List<Book> getBooksByAuthor(@Argument String authorId) {
        return bookRepo.findByAuthorId(authorId);
    }

    @QueryMapping
    public Author getAuthor(@Argument String name) {
        return authorRepo.findByName(name);
    }


}
