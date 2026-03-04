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

import java.net.URI;
import java.util.*;

@Transactional
@Controller("/books")
public class BooksController {

    @Inject
    private BookRepository repository;

    @Get
    public List<BookDTO> getBooks() {
        List<Book> books = repository.findAll();
        return books.stream().map(Book::toDTO).toList();
    }

    @Get("/{id}")
    public HttpResponse<BookDTO> getBook(@PathVariable Long id) {
        Optional<Book> o = repository.findById(id);
        if ( o.isPresent() ) {
            return HttpResponse.ok(o.get().toDTO());
        }else{
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
    }

    @Post
    public HttpResponse<BookDTO> createBook(@Body BookCreateDTO dto) {

        if (repository.existsByTitleAndAuthor(dto.getTitle(), dto.getAuthor())) {
            return  HttpResponse.status(HttpStatus.CONFLICT);
        }else{
            Book saved = repository.save(dto.toBook());
            return HttpResponse.created(saved.toDTO());
        }
    }

    @Put("/{id}")
    public HttpResponse<BookDTO> updateBook(@PathVariable Long id, @Body BookCreateDTO bookUpdate) {

        Optional<Book> o = repository.findById(id);

        if ( o.isPresent() ) {
            Book book = o.get();
            book.setTitle(bookUpdate.getTitle());
            book.setAuthor(bookUpdate.getAuthor());

            return HttpResponse.ok(book.toDTO());

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


