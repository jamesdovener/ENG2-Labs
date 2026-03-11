package uk.ac.york.cs.eng2.books;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import uk.ac.york.cs.eng2.books.domain.Publisher;
import uk.ac.york.cs.eng2.books.dto.PublisherDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends PageableRepository<Publisher, Long> {
    List<PublisherDTO> getAll();

    Optional<Publisher> findByBooksId(Long id);
}
