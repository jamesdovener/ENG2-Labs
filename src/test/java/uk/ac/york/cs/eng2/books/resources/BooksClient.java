package uk.ac.york.cs.eng2.books.resources;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.cs.eng2.books.dto.Book;
import uk.ac.york.cs.eng2.books.dto.BookUpdateDTO;

import java.util.List;

@Client("/books")
public interface BooksClient {
    @Get
    List<Book> getBooks();

    @Get("/{id}")
    Book getBook(@PathVariable int id);

    @Post
    HttpResponse<Book> createBook(@Body Book book);

    @Put("/{id}")
    HttpResponse<?> updateBook( @PathVariable int id,
                                @Body BookUpdateDTO bookUpdateDTO);

    @Delete("/{id}")
    HttpResponse<?> deleteBook(@PathVariable int id);


}
