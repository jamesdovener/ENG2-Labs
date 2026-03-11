package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.cs.eng2.books.dto.BookDTO;
import uk.ac.york.cs.eng2.books.dto.PublisherDTO;

import java.util.List;

@Client("/publishers")
public interface PublisherClient {
    @Get
    List<PublisherDTO> getPublishers();

    @Get("/{id}")
    HttpResponse<PublisherDTO> getPublisher(@PathVariable Long id);

    @Post
    HttpResponse<PublisherDTO> createPublisher(@Body PublisherDTO dto);

    @Put("/{id}")
    HttpResponse<PublisherDTO> updatePublisher(@PathVariable Long id, @Body PublisherDTO dto);

    @Delete("/{id}")
    HttpResponse<Void> deletePublisher(@PathVariable Long id);

    @Get("/{id}/books")
    HttpResponse<List<BookDTO>> getBooksByPublisher(@PathVariable Long id);
}