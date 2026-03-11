package uk.ac.york.cs.eng2.books.resources;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.cs.eng2.books.dto.BookDTO;
import uk.ac.york.cs.eng2.books.dto.BookCreateDTO;
import uk.ac.york.cs.eng2.books.dto.PublisherDTO;

import java.util.List;

@Client("/books")
public interface BooksClient {
    @Get
    List<BookDTO> getBooks();

    @Get("/{id}")
    BookDTO getBook(@PathVariable Long id);

    @Post
    HttpResponse<BookDTO> createBook(@Body BookCreateDTO dto);

    @Put("/{id}")
    HttpResponse<?> updateBook( @PathVariable Long id,
                                @Body BookCreateDTO bookCreateDTO);

    @Delete("/{id}")
    HttpResponse<?> deleteBook(@PathVariable Long id);

    @Get("/{id}/publisher")
    HttpResponse<PublisherDTO> getBookPublisher(@PathVariable Long id);

    @Put("/{id}/authors/{authorId}")
    HttpResponse<Void> addBookAuthor(@PathVariable Long id, @PathVariable Long authorId);

    @Delete("/{id}/authors/{authorId}")
    HttpResponse<Void> deleteBookAuthor(@PathVariable Long id, @PathVariable Long authorId);
}
