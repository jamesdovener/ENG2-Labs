package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.AuthorRepository;
import uk.ac.york.cs.eng2.books.BookRepository;
import uk.ac.york.cs.eng2.books.dto.AuthorCreateDTO;
import uk.ac.york.cs.eng2.books.dto.AuthorDTO;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
public class AuthorControllerTest {

    @Inject
    private AuthorsClient authorsClient;

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        // deleteById goes through Hibernate entity lifecycle, cascading join table cleanup
        bookRepository.findAll().forEach(b -> bookRepository.deleteById(b.getId()));
        authorRepository.deleteAll();
    }

    @Test
    public void noAuthors() {
        assertEquals(0, authorsClient.getAuthors().size());
    }

    @Test
    public void createAuthor() {
        AuthorDTO result = authorsClient.createAuthor(new AuthorCreateDTO("Author 1"));
        assertNotNull(result.getId());
        assertEquals("Author 1", result.getName());
    }

    @Test
    public void getAuthors() {
        authorsClient.createAuthor(new AuthorCreateDTO("Author 1"));
        assertEquals(1, authorsClient.getAuthors().size());
    }

    @Test
    public void getAuthorById() {
        AuthorDTO created = authorsClient.createAuthor(new AuthorCreateDTO("Author 1"));
        AuthorDTO found = authorsClient.getAuthor(created.getId());
        assertEquals(created, found);
    }

    @Test
    public void getAuthorByIdNotFound() {
        assertNull(authorsClient.getAuthor(0L));
    }

    @Test
    public void updateAuthor() {
        AuthorDTO created = authorsClient.createAuthor(new AuthorCreateDTO("Author 1"));
        HttpResponse<AuthorDTO> response = authorsClient.updateAuthor(created.getId(), new AuthorCreateDTO("Updated"));
        assertEquals(HttpStatus.OK, response.status());
        assertEquals("Updated", response.body().getName());
    }

    @Test
    public void updateAuthorNotFound() {
        HttpResponse<AuthorDTO> response = authorsClient.updateAuthor(0L, new AuthorCreateDTO("Updated"));
        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }

    @Test
    public void deleteAuthor() {
        AuthorDTO created = authorsClient.createAuthor(new AuthorCreateDTO("Author 1"));
        HttpResponse<Void> response = authorsClient.deleteAuthor(created.getId());
        assertEquals(HttpStatus.NO_CONTENT, response.status());
        assertNull(authorsClient.getAuthor(created.getId()));
    }

    @Test
    public void deleteAuthorNotFound() {
        HttpResponse<Void> response = authorsClient.deleteAuthor(0L);
        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }
}
