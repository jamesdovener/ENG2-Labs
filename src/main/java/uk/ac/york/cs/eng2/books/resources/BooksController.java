package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import uk.ac.york.cs.eng2.books.dto.Book;

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
            return  HttpResponse.badRequest();
        }else{
            books.put(book.getId(), book);
            return HttpResponse.created(book);
        }
    }

    @Put
    public HttpResponse<?> updateBook(@Body Book book) {

        if ( books.containsKey(book.getId()) ) {
            books.put(book.getId(), book);
            return HttpResponse.ok();

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




