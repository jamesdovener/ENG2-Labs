package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.dto.BookCreateDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest(transactional = false)
public class BookCreateDTOTest {

    private String author;
    private String title;


    @BeforeEach
    public void setUp() {
        author = "author";
        title = "title";
    }

    @Test
    public void testConstructor() {
        BookCreateDTO dto = new BookCreateDTO(author, title);
        assertEquals(author, dto.getAuthor());
        assertEquals(title, dto.getTitle());
    }
}
