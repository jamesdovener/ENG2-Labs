package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.dto.Author;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(rebuildContext = true)
public class AuthorControllerTest {

    @Inject
    private AuthorsClient authorsClient;

    private Author author;

    @BeforeEach
    public void setup() {
        author = new Author();
        author.setId(0);
        author.setFirstName("John");
        author.setLastName("Doe");
    }

    @Test
    public void noAuthors() {
        assertEquals(0, authorsClient.getAuthors().size());
    }

    @Test
    public void addAuthor() {
        authorsClient.createAuthor(author);
        assertEquals(author, authorsClient.getAuthors().get(0));
    }

    @Test
    public void createExistingAuthor() {
        authorsClient.createAuthor(author);
        System.out.println(authorsClient.getAuthors());
        HttpClientResponseException exception =
                assertThrows(HttpClientResponseException.class,
                () -> authorsClient.createAuthor(author)
                );
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    public void getAuthorById() {
        authorsClient.createAuthor(author);

        Author retrieved_author = authorsClient.getAuthor(author.getId());
        assertEquals(author, retrieved_author);
    }
}
