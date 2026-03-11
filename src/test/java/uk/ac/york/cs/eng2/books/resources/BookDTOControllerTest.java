package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.cs.eng2.books.AuthorRepository;
import uk.ac.york.cs.eng2.books.BookRepository;
import uk.ac.york.cs.eng2.books.PublisherRepository;
import uk.ac.york.cs.eng2.books.domain.Book;
import uk.ac.york.cs.eng2.books.domain.Publisher;
import uk.ac.york.cs.eng2.books.dto.AuthorCreateDTO;
import uk.ac.york.cs.eng2.books.dto.AuthorDTO;
import uk.ac.york.cs.eng2.books.dto.PublisherDTO;
import uk.ac.york.cs.eng2.books.dto.BookDTO;
import uk.ac.york.cs.eng2.books.dto.BookCreateDTO;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
public class BookDTOControllerTest {

    @Inject
    private BooksClient booksClient;
    @Inject
    private BookRepository bookRepository;

    @Inject
    private PublisherRepository publisherRepository;

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    private AuthorsClient authorsClient;

    private BookDTO bookDTO;
    private BookCreateDTO bookCreateDTO;
    private BookCreateDTO updatedDTO;
    private Publisher publisher;

    @BeforeEach
    void setUp(){
        bookDTO = new BookDTO();
        bookCreateDTO = new BookCreateDTO();
        updatedDTO = new BookCreateDTO();
        // deleteById cascades join table cleanup via Hibernate entity lifecycle
        bookRepository.findAll().forEach(b -> bookRepository.deleteById(b.getId()));
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
        publisher = new Publisher();

        publisher.setName("Publisher 1");
        publisher = publisherRepository.save(publisher);


        bookDTO.setTitle("Title 1");
        bookDTO.setPublisher(publisher.toDTO());

        updatedDTO.setTitle("Updated Title");

        bookCreateDTO.setTitle("Title 1");
        bookCreateDTO.setPublisherDTO(publisher.toDTO());
    }

    @Test
    public void noBooks(){
        // Assert
        assertEquals(0, booksClient.getBooks().size());
    }

    @Test
    public void addBook(){
        // Act
        HttpResponse<BookDTO> response = booksClient.createBook(bookCreateDTO);
        bookDTO.setId(response.body().getId());
        // Assert
        assertEquals(bookDTO, booksClient.getBooks().get(0));
    }

    @Test
    public void getBook(){
        // Act
        HttpResponse<BookDTO> response = booksClient.createBook(bookCreateDTO);
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
        HttpResponse<BookDTO> response = booksClient.createBook(bookCreateDTO);
        Long id = response.body().getId();
        // Act
        booksClient.updateBook(id, updatedDTO);
        // Assert
        assertEquals("Updated Title", booksClient.getBook(id).getTitle());
    }

//    @Test
//    public void updateAuthor(){
//        // Arrange
//        HttpResponse<BookDTO> response = booksClient.createBook(bookCreateDTO);
//        Long id = response.body().getId();
//        // Act
//        booksClient.updateBook(id, updatedDTO);
//        // Assert
//        assertEquals("Updated Author", booksClient.getBook(id).getAuthor());
//    }

    @Test
    public void deleteBook(){
        HttpResponse<BookDTO> response = booksClient.createBook(bookCreateDTO);
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

//    @Test
//    public void createExistingBook(){
//
//        HttpResponse<BookDTO> response = booksClient.createBook(bookCreateDTO);
//        assertEquals(HttpStatus.CREATED, response.status());
//
//        HttpClientResponseException exception =
//                assertThrows(HttpClientResponseException.class,
//                () -> booksClient.createBook(bookCreateDTO)
//        );
//
//        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
//    }

    @Test
    public void getBookPublisher() {
        HttpResponse<BookDTO> bookResponse = booksClient.createBook(bookCreateDTO);
        Long bookId = bookResponse.body().getId();

        HttpResponse<PublisherDTO> response = booksClient.getBookPublisher(bookId);

        assertEquals(HttpStatus.OK, response.status());
        assertEquals("Publisher 1", response.body().getName());

    }

    @Test
    public void getBookPublisherBookNotFound() {
        HttpResponse<PublisherDTO> response = booksClient.getBookPublisher(0L);
        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }

    @Test
    public void getBookPublisherNoPublisher() {
        // Save a book directly with no publisher set
        Book book = new Book();
        book.setTitle("No Publisher Book");
        book = bookRepository.save(book);
        HttpResponse<PublisherDTO> response = booksClient.getBookPublisher(book.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }

    @Test
    public void addBookAuthor() {
        Long bookId = booksClient.createBook(bookCreateDTO).body().getId();
        AuthorDTO author = authorsClient.createAuthor(new AuthorCreateDTO("Test Author"));
        booksClient.addBookAuthor(bookId, author.getId());
        assertEquals(1, booksClient.getBook(bookId).getAuthors().size());
    }

    @Test
    public void addBookAuthorNotFound() {
        Long bookId = booksClient.createBook(bookCreateDTO).body().getId();
        // Author does not exist
        HttpResponse<Void> response = booksClient.addBookAuthor(bookId, 0L);
        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }

    @Test
    public void addBookNotFound() {
        AuthorDTO author = authorsClient.createAuthor(new AuthorCreateDTO("Test Author"));
        // Book does not exist
        HttpResponse<Void> response = booksClient.addBookAuthor(0L, author.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.status());
    }

    @Test
    public void deleteBookAuthor() {
        Long bookId = booksClient.createBook(bookCreateDTO).body().getId();
        AuthorDTO author = authorsClient.createAuthor(new AuthorCreateDTO("Test Author"));
        booksClient.addBookAuthor(bookId, author.getId());
        booksClient.deleteBookAuthor(bookId, author.getId());
        BookDTO bookAfter = booksClient.getBook(bookId);
        assertTrue(bookAfter.getAuthors() == null || bookAfter.getAuthors().isEmpty());
    }
}
