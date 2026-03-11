package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.AuthorRepository;
import uk.ac.york.cs.eng2.books.BookRepository;
import uk.ac.york.cs.eng2.books.PublisherRepository;
import uk.ac.york.cs.eng2.books.dto.BookCreateDTO;
import uk.ac.york.cs.eng2.books.dto.BookDTO;
import uk.ac.york.cs.eng2.books.dto.PublisherDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
public class PublisherControllerTest {

    @Inject
    private PublisherClient publisherClient;

    @Inject
    private PublisherRepository publisherRepository;

    @Inject
    private BookRepository bookRepository;

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    private BooksClient booksClient;

    @BeforeEach
    void setUp() {
        bookRepository.findAll().forEach(b -> bookRepository.deleteById(b.getId()));
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    @Test
    public void noPublishers() {
        assertEquals(0, publisherClient.getPublishers().size());
    }

    @Test
    public void createPublisher() {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("Publisher 1");
        HttpResponse<PublisherDTO> response = publisherClient.createPublisher(dto);
        assertEquals(HttpStatus.CREATED, response.status());
        assertEquals("Publisher 1", response.body().getName());
        assertNotNull(response.body().getId());
    }

    @Test
    public void getPublishers() {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("Publisher 1");
        publisherClient.createPublisher(dto);
        assertEquals(1, publisherClient.getPublishers().size());
    }

    @Test
    public void getPublisher() {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("Publisher 1");
        Long id = publisherClient.createPublisher(dto).body().getId();
        HttpResponse<PublisherDTO> response = publisherClient.getPublisher(id);
        assertEquals(HttpStatus.OK, response.status());
        assertEquals("Publisher 1", response.body().getName());
    }

    @Test
    public void getPublisherNotFound() {
        assertEquals(HttpStatus.NOT_FOUND, publisherClient.getPublisher(0L).status());
    }

    @Test
    public void updatePublisher() {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("Publisher 1");
        Long id = publisherClient.createPublisher(dto).body().getId();
        PublisherDTO updateDTO = new PublisherDTO();
        updateDTO.setName("Updated");
        HttpResponse<PublisherDTO> response = publisherClient.updatePublisher(id, updateDTO);
        assertEquals(HttpStatus.CREATED, response.status());
        assertEquals("Updated", response.body().getName());
    }

    @Test
    public void updatePublisherNotFound() {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("Updated");
        assertEquals(HttpStatus.NOT_FOUND, publisherClient.updatePublisher(0L, dto).status());
    }

    @Test
    public void deletePublisher() {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("Publisher 1");
        Long id = publisherClient.createPublisher(dto).body().getId();
        assertEquals(HttpStatus.NO_CONTENT, publisherClient.deletePublisher(id).status());
        assertEquals(HttpStatus.NOT_FOUND, publisherClient.getPublisher(id).status());
    }

    @Test
    public void getBooksByPublisher() {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("Publisher 1");
        Long id = publisherClient.createPublisher(dto).body().getId();
        HttpResponse<List<BookDTO>> response = publisherClient.getBooksByPublisher(id);
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(0, response.body().size());
    }

    @Test
    public void getBooksByPublisherWithBooks() {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("Publisher 1");
        PublisherDTO created = publisherClient.createPublisher(dto).body();
        booksClient.createBook(new BookCreateDTO("Book 1", created));
        HttpResponse<List<BookDTO>> response = publisherClient.getBooksByPublisher(created.getId());
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(1, response.body().size());
    }

    @Test
    public void getBooksByPublisherNotFound() {
        assertEquals(HttpStatus.NOT_FOUND, publisherClient.getBooksByPublisher(0L).status());
    }
}
