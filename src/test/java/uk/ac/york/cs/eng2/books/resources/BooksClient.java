package uk.ac.york.cs.eng2.books.resources;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.cs.eng2.books.dto.Book;

import java.util.List;

@Client("/books")
public interface BooksClient {
    @Get
    List<Book> getBooks();

    @Get("/{id}")
    Book getBook(@PathVariable int id);

    @Post
    void createBook(@Body Book book);

    @Put
    HttpResponse updateBook(@Body Book book);

    @Delete("/{id}")
    HttpResponse deleteBook(@PathVariable int id);


}
