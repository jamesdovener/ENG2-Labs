package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.dto.Book;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(rebuildContext = true)
public class BookControllerTest {

    @Inject
    private BooksClient booksClient;

    private Book book;

    @BeforeEach
    void setUp(){
        book = new Book();
        book.setId(1);
        book.setAuthor("Author 1");
        book.setTitle("Title 1");
    }

    @Test
    public void noBooks(){
        // Assert
        assertEquals(0, booksClient.getBooks().size());
    }

    @Test
    public void addBook(){
        // Act
        booksClient.createBook(book);
        // Assert
        assertEquals(book, booksClient.getBooks().get(0));
    }

    @Test
    public void getBook(){
        // Act
        booksClient.createBook(book);
        // Assert
        assertEquals(book, booksClient.getBook(1));
    }

    @Test
    public void bookDoesNotExist(){
        // Assert
        assertNull(booksClient.getBook(0));
    }

    @Test
    public void updateTitle(){
        // Arrange
        booksClient.createBook(book);
        book.setTitle("Updated Title");
        // Act
        booksClient.updateBook(book);
        // Assert
        assertEquals("Updated Title", booksClient.getBook(1).getTitle());
    }

    @Test
    public void updateAuthor(){
        // Arrange
        booksClient.createBook(book);
        book.setAuthor("Updated Author");
        // Act
        booksClient.updateBook(book);
        // Assert
        assertEquals("Updated Author", booksClient.getBook(1).getAuthor());
    }

    @Test
    public void deleteBook(){
        booksClient.createBook(book);
        booksClient.deleteBook(1);
        assertNull(booksClient.getBook(1));
    }

    @Test
    public void updateNullBook(){
        assertEquals(HttpStatus.NOT_FOUND, booksClient.updateBook(book).getStatus());
    }

    @Test
    public void deleteNullBook(){
        HttpResponse response = booksClient.deleteBook(1);
        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }
}
