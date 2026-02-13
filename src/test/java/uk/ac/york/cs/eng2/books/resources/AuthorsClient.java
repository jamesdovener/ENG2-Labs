package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.cs.eng2.books.dto.Author;

import java.util.List;

@Client("/authors")
public interface AuthorsClient {
    @Get
    List<Author> getAuthors();

    @Get("/{id}")
    Author getAuthor(@PathVariable int id);

    @Post
    Author createAuthor(@Body Author author);
}
