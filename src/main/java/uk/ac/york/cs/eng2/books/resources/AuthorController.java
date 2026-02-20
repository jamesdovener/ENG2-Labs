package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import uk.ac.york.cs.eng2.books.dto.AuthorDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/authors")
public class AuthorController {

    private Map<Integer, AuthorDTO> authors = new HashMap<>();

    @Post
    public HttpResponse<AuthorDTO> createAuthor(@Body AuthorDTO authorDTO) {
        if  (authors.containsKey(authorDTO.getId())) {
            return HttpResponse.status(HttpStatus.CONFLICT);
        }else{
            authors.put(authorDTO.getId(), authorDTO);
            return HttpResponse.ok(authorDTO);
        }
    }

    @Get
    public List<AuthorDTO> getAuthors() {
        return new ArrayList<>(authors.values());
    }

    @Get("/{id}")
    public AuthorDTO getAuthor(@PathVariable int id) {
        return authors.get(id);
    }
}
