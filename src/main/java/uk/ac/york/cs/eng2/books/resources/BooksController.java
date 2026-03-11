package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import uk.ac.york.cs.eng2.books.AuthorRepository;
import uk.ac.york.cs.eng2.books.BookRepository;
import uk.ac.york.cs.eng2.books.PublisherRepository;
import uk.ac.york.cs.eng2.books.domain.Author;
import uk.ac.york.cs.eng2.books.domain.Book;
import uk.ac.york.cs.eng2.books.domain.Publisher;
import uk.ac.york.cs.eng2.books.dto.BookDTO;
import uk.ac.york.cs.eng2.books.dto.BookCreateDTO;
import uk.ac.york.cs.eng2.books.dto.PublisherDTO;

import java.util.*;

@Transactional
@Controller("/books")
public class BooksController {

    @Inject
    private BookRepository repo;

    @Inject
    private PublisherRepository publisherRepository;
    @Inject
    private AuthorRepository authorRepository;

    @Get
    public List<BookDTO> getBooks() {
        return repo.findAll().stream().map(Book::toDTO).toList();
    }

    @Get("/{id}")
    public HttpResponse<BookDTO> getBook(@PathVariable Long id) {
        Optional<Book> o = repo.findById(id);
        if ( o.isPresent() ) {
            return HttpResponse.ok(o.get().toDTO());
        }else{
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
    }

    @Get("/{id}/publisher")
    public HttpResponse<PublisherDTO> getBookPublisher(@PathVariable Long id) {

        // Check that book exists
        if ( repo.existsById(id) ) {
            Optional<Publisher> o = publisherRepository.findByBooksId(id);

            if ( o.isPresent() ) {
                return HttpResponse.ok(o.get().toDTO());
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND,
                        "No publisher exists for book with id: " + id);
            }

        }else{
            return HttpResponse.status(HttpStatus.NOT_FOUND,
                    "Book with id " + id + " not found");
        }

    }



    @Post
    public HttpResponse<BookDTO> createBook(@Body BookCreateDTO dto) {

        Book saved = repo.save(dto.toBook());

        return HttpResponse.created(saved.toDTO());
    }

    @Put("/{id}")
    public HttpResponse<BookDTO> updateBook(@PathVariable Long id, @Body BookCreateDTO bookUpdate) {

        Optional<Book> o = repo.findById(id);

        if ( o.isPresent() ) {
            Book book = o.get();
            book.setTitle(bookUpdate.getTitle());

            return HttpResponse.ok(book.toDTO());

        }else{
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
    }

    @Put("/{id}/authors/{authorId}")
    public void addBookAuthor(@PathVariable Long id, @PathVariable Long authorId) {
        BookAuthor result = getBookAuthor(id, authorId);
        result.book.getAuthors().add(result.author);
    }

    @Delete("{id}/authors/{authorId}")
    public void deleteBookAuthor(@PathVariable Long id, @PathVariable Long authorId) {
        BookAuthor result = getBookAuthor(id, authorId);
        result.book.getAuthors().remove(result.author);
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteBook(@PathVariable Long id) {

        Optional<Book> o = repo.findById(id);

        if  ( o.isPresent() ) {
            repo.deleteById(id);
            return HttpResponse.ok();
        }else{
            return HttpResponse.notFound();
        }
    }

    private record BookAuthor(Author author, Book book) {}

    private BookAuthor getBookAuthor(long id, long authorId) {

        Optional<Author> oAuthor = authorRepository.findById(authorId);

        if ( !oAuthor.isPresent() ) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
        Author author = oAuthor.get();


        Optional<Book> oBook = repo.findById(id);
        if ( !oBook.isPresent() ) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        Book book = oBook.get();
        BookAuthor result = new BookAuthor(author, book);

        return result;
    }
}


