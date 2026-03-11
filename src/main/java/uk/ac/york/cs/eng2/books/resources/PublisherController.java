package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import uk.ac.york.cs.eng2.books.BookRepository;
import uk.ac.york.cs.eng2.books.PublisherRepository;
import uk.ac.york.cs.eng2.books.domain.Book;
import uk.ac.york.cs.eng2.books.domain.Publisher;
import uk.ac.york.cs.eng2.books.dto.BookDTO;
import uk.ac.york.cs.eng2.books.dto.PublisherDTO;

import java.util.List;
import java.util.Optional;

@Transactional
@Controller("/publishers")
public class PublisherController {

    @Inject
    PublisherRepository repo;
    @Inject
    private BookRepository bookRepository;


    @Get
    public HttpResponse<List<PublisherDTO>> getPublishers(){
        List<PublisherDTO> publishers = repo.getAll();
        return HttpResponse.ok(publishers);
    }

    @Get("/{id}")
    public HttpResponse<PublisherDTO> getPublisher(@PathVariable Long id) {
        Optional<Publisher> o = repo.findById(id);
        if ( o.isPresent() ) {
            return HttpResponse.ok(o.get().toDTO());
        } else {
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
    }

    @Post
    public HttpResponse<PublisherDTO> createPublisher(@Body PublisherDTO dto) {
        Publisher publisher = new Publisher();
        publisher.setName(dto.getName());
        Publisher saved = repo.save(publisher);
        return HttpResponse.created(saved.toDTO());
    }

    @Put("/{id}")
    public HttpResponse<PublisherDTO> updatePublisher(@PathVariable Long id, @Body PublisherDTO dto) {
        Optional<Publisher> o = repo.findById(id);
        if ( o.isPresent() ) {
            Publisher publisher = o.get();
            publisher.setName(dto.getName());
            Publisher saved = repo.save(publisher);
            return HttpResponse.created(saved.toDTO());
        }else{
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
    }

    @Delete("/{id}")
    public HttpResponse<Void> deletePublisher(@PathVariable Long id) {
        repo.deleteById(id);
        return HttpResponse.noContent();
    }

    @Get("/{id}/books")
    public HttpResponse<List<BookDTO>> getBooksByPublisher(@PathVariable Long id) {
        if ( repo.existsById(id) ) {

            return HttpResponse.ok(bookRepository.findByPublisherId(id)
                    .stream().map(Book::toDTO).toList());
        }else{
            return HttpResponse.status(HttpStatus.NOT_FOUND,
                    "Publisher with id: "+ id + " not found");
        }

    }
    
}
