package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.cs.eng2.books.dto.AuthorDTO;

import java.util.List;

@Client("/authors")
public interface AuthorsClient {
    @Get
    List<AuthorDTO> getAuthors();

    @Get("/{id}")
    AuthorDTO getAuthor(@PathVariable int id);

    @Post
    AuthorDTO createAuthor(@Body AuthorDTO authorDTO);
}
