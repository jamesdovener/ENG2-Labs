package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.cs.eng2.books.dto.AuthorCreateDTO;
import uk.ac.york.cs.eng2.books.dto.AuthorDTO;

import java.util.List;

@Client("/authors")
public interface AuthorsClient {
    @Get
    List<AuthorDTO> getAuthors();

    @Get("/{id}")
    AuthorDTO getAuthor(@PathVariable Long id);

    @Post
    AuthorDTO createAuthor(@Body AuthorCreateDTO dto);

    @Put("/{id}")
    HttpResponse<AuthorDTO> updateAuthor(@PathVariable Long id, @Body AuthorCreateDTO dto);

    @Delete("/{id}")
    HttpResponse<Void> deleteAuthor(@PathVariable Long id);
}