package uk.ac.york.cs.eng2.books.resources;

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
    public void createBook(@Body Book book) {
        books.put(book.getId(), book);
    }

    @Put
    public void updateBook(@Body Book book) {
        books.put(book.getId(), book);
    }

    @Delete
    public void deleteBook(@Body Book book) {
        books.remove(book.getId());
    }
}




