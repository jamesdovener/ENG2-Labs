package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import uk.ac.york.cs.eng2.books.AuthorRepository;
import uk.ac.york.cs.eng2.books.domain.Author;
import uk.ac.york.cs.eng2.books.dto.AuthorCreateDTO;
import uk.ac.york.cs.eng2.books.dto.AuthorDTO;

import java.util.List;

@Transactional
@Controller("/authors")
public class AuthorController {

    @Inject
    private AuthorRepository repo;

    @Post
    public HttpResponse<AuthorDTO> createAuthor(@Body AuthorCreateDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        Author saved = repo.save(author);
        return HttpResponse.created(saved.toDTO());
    }

    @Get
    public List<AuthorDTO> getAuthors() { return repo.getAll(); }

    @Get("/{id}")
    public HttpResponse<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Author author = repo.findById(id).orElse(null);
        if(author == null) {
            return HttpResponse.notFound();
        }else{
            return HttpResponse.ok(author.toDTO());
        }
    }

    @Put("/{id}")
    public HttpResponse<AuthorDTO> updateAuthor(@PathVariable Long id, @Body AuthorCreateDTO dto) {
        Author author = repo.findById(id).orElse(null);

        if(author == null) {
            return  HttpResponse.notFound();
        }else{
            author.setName(dto.getName());
            Author saved = repo.save(author);
            return HttpResponse.ok(saved.toDTO());
        }
    }

    @Delete("/{id}")
    public HttpResponse<Void> deleteAuthor(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return HttpResponse.notFound();
        }else{
            repo.deleteById(id);
            return HttpResponse.noContent();
        }
    }

}
