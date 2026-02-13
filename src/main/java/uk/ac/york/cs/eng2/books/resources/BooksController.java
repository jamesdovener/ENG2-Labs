package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import uk.ac.york.cs.eng2.books.dto.Book;
import uk.ac.york.cs.eng2.books.dto.BookUpdateDTO;

import java.util.*;

@Controller("/books")
public class BooksController {

    private Map<Integer, Book> books = new HashMap<>();

    @Get
    public List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    @Get("/{id}")
    public Book getBook(@PathVariable int id) {
        return books.get(id);
    }

    @Post
    public HttpResponse<Book> createBook(@Body Book book) {

        if ( books.containsKey(book.getId()) ) {
            return  HttpResponse.status(HttpStatus.CONFLICT);
        }else{
            books.put(book.getId(), book);
            return HttpResponse.created(book);
        }
    }

    @Put("/{id}")
    public HttpResponse<?> updateBook(@PathVariable int id, @Body BookUpdateDTO bookUpdate) {

        if ( books.containsKey(id) ) {
            Book book = books.get(id);
            book.setTitle(bookUpdate.getTitle());
            book.setAuthor(bookUpdate.getAuthor());

            return HttpResponse.ok(book);

        }else{
            return HttpResponse.notFound();
        }
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteBook(@PathVariable int id) {
        if  (books.containsKey(id)) {
            books.remove(id);
            return HttpResponse.ok();
        }else{
            return HttpResponse.notFound();
        }
    }
}




