package uk.ac.york.cs.eng2.books.resources;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.cs.eng2.books.dto.BookDTO;
import uk.ac.york.cs.eng2.books.dto.BookUpdateDTO;

import java.util.List;

@Client("/books")
public interface BooksClient {
    @Get
    List<BookDTO> getBooks();

    @Get("/{id}")
    BookDTO getBook(@PathVariable Long id);

    @Post
    HttpResponse<BookDTO> createBook(@Body BookDTO bookDTO);

    @Put("/{id}")
    HttpResponse<?> updateBook( @PathVariable Long id,
                                @Body BookUpdateDTO bookUpdateDTO);

    @Delete("/{id}")
    HttpResponse<?> deleteBook(@PathVariable Long id);


}
