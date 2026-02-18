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
import uk.ac.york.cs.eng2.books.dto.BookUpdateDTO;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
public class BookDTOControllerTest {

    @Inject
    private BooksClient booksClient;
    private BookRepository bookRepository;
    private BookDTO bookDTO;
    private BookUpdateDTO bookUpdate;

    @BeforeEach
    void setUp(){
        bookDTO = new BookDTO();
        bookUpdate = new BookUpdateDTO();

        bookDTO.setId(1L);
        bookDTO.setAuthor("Author 1");
        bookDTO.setTitle("Title 1");

        bookUpdate.setAuthor("Updated Author");
        bookUpdate.setTitle("Updated Title");

    }

    @Test
    public void noBooks(){
        // Assert
        assertEquals(0, booksClient.getBooks().size());
    }

    @Test
    public void addBook(){
        // Act
        booksClient.createBook(bookDTO);
        // Assert
        assertEquals(bookDTO, booksClient.getBooks().get(0));
    }

    @Test
    public void getBook(){
        // Act
        booksClient.createBook(bookDTO);
        // Assert
        assertEquals(bookDTO, booksClient.getBook(1L));
    }

    @Test
    public void bookDoesNotExist(){
        // Assert
        assertNull(booksClient.getBook(0L));
    }

    @Test
    public void updateTitle(){
        // Arrange
        booksClient.createBook(bookDTO);
        // Act
        booksClient.updateBook(bookDTO.getId(), bookUpdate);
        // Assert
        assertEquals("Updated Title", booksClient.getBook(1L).getTitle());
    }

    @Test
    public void updateAuthor(){
        // Arrange
        booksClient.createBook(bookDTO);
        // Act
        booksClient.updateBook(bookDTO.getId(), bookUpdate);
        // Assert
        assertEquals("Updated Author", booksClient.getBook(1L).getAuthor());
    }

    @Test
    public void deleteBook(){
        booksClient.createBook(bookDTO);
        booksClient.deleteBook(1L);
        assertNull(booksClient.getBook(1L));
    }

    @Test
    public void updateNullBook(){

        assertEquals(HttpStatus.NOT_FOUND, booksClient.updateBook(bookDTO.getId(), bookUpdate).getStatus());
    }

    @Test
    public void deleteNullBook(){

        HttpResponse<?> response = booksClient.deleteBook(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }

    @Test
    public void createExistingBook(){

        booksClient.createBook(bookDTO);

        HttpClientResponseException exception =
                assertThrows(HttpClientResponseException.class,
                () -> booksClient.createBook(bookDTO)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }
}
