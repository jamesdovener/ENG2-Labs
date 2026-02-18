package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.dto.AuthorDTO;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(rebuildContext = true)
public class AuthorDTOControllerTest {

    @Inject
    private AuthorsClient authorsClient;

    private AuthorDTO authorDTO;

    @BeforeEach
    public void setup() {
        authorDTO = new AuthorDTO();
        authorDTO.setId(0);
        authorDTO.setFirstName("John");
        authorDTO.setLastName("Doe");
    }

    @Test
    public void noAuthors() {
        assertEquals(0, authorsClient.getAuthors().size());
    }

    @Test
    public void addAuthor() {
        authorsClient.createAuthor(authorDTO);
        assertEquals(authorDTO, authorsClient.getAuthors().get(0));
    }

    @Test
    public void createExistingAuthor() {
        authorsClient.createAuthor(authorDTO);
        System.out.println(authorsClient.getAuthors());
        HttpClientResponseException exception =
                assertThrows(HttpClientResponseException.class,
                () -> authorsClient.createAuthor(authorDTO)
                );
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    public void getAuthorById() {
        authorsClient.createAuthor(authorDTO);

        AuthorDTO retrieved_authorDTO = authorsClient.getAuthor(authorDTO.getId());
        assertEquals(authorDTO, retrieved_authorDTO);
    }
}
