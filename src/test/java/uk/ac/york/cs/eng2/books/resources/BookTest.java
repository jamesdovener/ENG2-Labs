package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.domain.Book;
import uk.ac.york.cs.eng2.books.dto.BookDTO;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
public class BookTest {

    private Long id;
    private String title;
    private String author;

    @BeforeEach
    void setUp() {
        id = 1L;
        author = "Author";
        title = "Title";
    }

    @Test
    public void testConstructor() {
        Book book = new Book(id, title);
        assertEquals(book.getId(), id);
        assertEquals(book.getTitle(), title);
    }

    @Test
    public void testToDTO() {
        Book book = new Book(id, title);
        BookDTO bookDTO = book.toDTO();

        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getTitle(), bookDTO.getTitle());
    }
}
