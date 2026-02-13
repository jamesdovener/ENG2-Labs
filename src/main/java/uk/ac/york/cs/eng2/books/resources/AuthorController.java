package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import uk.ac.york.cs.eng2.books.dto.Author;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/authors")
public class AuthorController {

    private Map<Integer, Author> authors = new HashMap<>();

    @Post
    public HttpResponse<Author> createAuthor(@Body Author author) {
        if  (authors.containsKey(author.getId())) {
            return HttpResponse.status(HttpStatus.CONFLICT);
        }else{
            authors.put(author.getId(), author);
            return HttpResponse.ok(author);
        }
    }

    @Get
    public List<Author> getAuthors() {
        return new ArrayList<>(authors.values());
    }

    @Get("/{id}")
    public Author getAuthor(@PathVariable int id) {
        return authors.get(id);
    }
}
