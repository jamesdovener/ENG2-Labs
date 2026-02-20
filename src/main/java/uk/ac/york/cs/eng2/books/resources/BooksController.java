package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import uk.ac.york.cs.eng2.books.BookRepository;
import uk.ac.york.cs.eng2.books.domain.Book;
import uk.ac.york.cs.eng2.books.dto.BookDTO;
import uk.ac.york.cs.eng2.books.dto.BookCreateDTO;

import java.util.*;

@Transactional
@Controller("/books")
public class BooksController {

    @Inject
    private BookRepository repository;

    public List<BookDTO> bookToBookDTO(List<Book> books) {

        return books.stream().
                map(Book -> new BookDTO(
                        Book.getId(),
                        Book.getAuthor(),
                        Book.getTitle()
                )).toList();
    }

    public BookDTO bookToBookDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getAuthor(),
                book.getTitle()
        );

    }

    public Book bookDTOToBook(BookDTO bookDTO){
        return new Book(
                bookDTO.getId(),
                bookDTO.getAuthor(),
                bookDTO.getTitle()
        );
    }



    @Get
    public List<BookDTO> getBooks() {
        List<Book> books = repository.findAll();
        return bookToBookDTO(books);
    }

    @Get("/{id}")
    public HttpResponse<BookDTO> getBook(@PathVariable Long id) {
        Optional<Book> o = repository.findById(id);
        if ( o.isPresent() ) {
            return HttpResponse.ok(bookToBookDTO(o.get()));
        }else{
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
    }

    @Post
    public HttpResponse<BookDTO> createBook(@Body BookDTO bookDTO) {

        if (repository.existsByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor())) {
            return  HttpResponse.status(HttpStatus.CONFLICT);
        }else{
            Book saved = repository.save(bookDTOToBook(bookDTO));
            return HttpResponse.created(bookToBookDTO(saved));
        }
    }

    @Put("/{id}")
    public HttpResponse<BookDTO> updateBook(@PathVariable Long id, @Body BookCreateDTO bookUpdate) {

        Optional<Book> o = repository.findById(id);

        if ( o.isPresent() ) {
            Book book = o.get();
            book.setTitle(bookUpdate.getTitle());
            book.setAuthor(bookUpdate.getAuthor());

            return HttpResponse.ok(bookToBookDTO(book));

        }else{
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteBook(@PathVariable Long id) {

        Optional<Book> o = repository.findById(id);

        if  ( o.isPresent() ) {
            repository.deleteById(id);
            return HttpResponse.ok();
        }else{
            return HttpResponse.notFound();
        }
    }
}


