package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.BookRepository;
import uk.ac.york.cs.eng2.books.dto.BookDTO;
import uk.ac.york.cs.eng2.books.dto.BookCreateDTO;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
public class BookDTOControllerTest {

    @Inject
    private BooksClient booksClient;
    @Inject
    private BookRepository bookRepository;
    private BookDTO bookDTO;
    private BookCreateDTO bookCreateDTO;

    @BeforeEach
    void setUp(){
        bookDTO = new BookDTO();
        bookCreateDTO = new BookCreateDTO();
        bookRepository.deleteAll();
        bookDTO.setAuthor("Author 1");
        bookDTO.setTitle("Title 1");

        bookCreateDTO.setAuthor("Author 1");
        bookCreateDTO.setTitle("Title 1");
    }

    @Test
    public void noBooks(){
        // Assert
        assertEquals(0, booksClient.getBooks().size());
    }

    @Test
    public void addBook(){
        // Act
        HttpResponse<BookDTO> response = booksClient.createBook(bookDTO);
        bookDTO.setId(response.body().getId());
        // Assert
        assertEquals(bookDTO, booksClient.getBooks().get(0));
    }

    @Test
    public void getBook(){
        // Act
        HttpResponse<BookDTO> response = booksClient.createBook(bookDTO);
        Long id = response.body().getId();
        bookDTO.setId(id);
        // Assert
        assertEquals(bookDTO, booksClient.getBook(id));
    }

    @Test
    public void bookDoesNotExist(){
        // Assert
        assertNull(booksClient.getBook(0L));
    }

    @Test
    public void updateTitle(){
        // Arrange
        HttpResponse<BookDTO> response = booksClient.createBook(bookDTO);
        Long id = response.body().getId();
        // Act
        booksClient.updateBook(id, bookCreateDTO);
        // Assert
        assertEquals("Updated Title", booksClient.getBook(id).getTitle());
    }

    @Test
    public void updateAuthor(){
        // Arrange
        HttpResponse<BookDTO> response = booksClient.createBook(bookDTO);
        Long id = response.body().getId();
        // Act
        booksClient.updateBook(id, bookCreateDTO);
        // Assert
        assertEquals("Updated Author", booksClient.getBook(id).getAuthor());
    }

    @Test
    public void deleteBook(){
        HttpResponse<BookDTO> response = booksClient.createBook(bookDTO);
        Long id = response.body().getId();
        booksClient.deleteBook(id);
        assertNull(booksClient.getBook(id));
    }

    @Test
    public void updateNullBook(){

        assertEquals(HttpStatus.NOT_FOUND, booksClient.updateBook(0L, bookCreateDTO).getStatus());
    }

    @Test
    public void deleteNullBook(){

        HttpResponse<?> response = booksClient.deleteBook(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }

    @Test
    public void createExistingBook(){

        HttpResponse<BookDTO> response = booksClient.createBook(bookDTO);
        assertEquals(HttpStatus.CREATED, response.status());

        HttpClientResponseException exception =
                assertThrows(HttpClientResponseException.class,
                () -> booksClient.createBook(bookDTO)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }
}
