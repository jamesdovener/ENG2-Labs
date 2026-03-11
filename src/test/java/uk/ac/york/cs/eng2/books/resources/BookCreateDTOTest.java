package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.domain.Publisher;
import uk.ac.york.cs.eng2.books.dto.BookCreateDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest(transactional = false)
public class BookCreateDTOTest {

    private String title;
    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        title = "title";
        publisher = new Publisher();
    }

    @Test
    public void testConstructor() {
        BookCreateDTO dto = new BookCreateDTO(title, publisher.toDTO());
        assertEquals(title, dto.getTitle());
        assertEquals(publisher, dto.getPublisherDTO().toPublisher());
    }
}
