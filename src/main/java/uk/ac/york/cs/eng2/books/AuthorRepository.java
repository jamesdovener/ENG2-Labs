package uk.ac.york.cs.eng2.books;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import uk.ac.york.cs.eng2.books.domain.Author;
import uk.ac.york.cs.eng2.books.dto.AuthorDTO;
import uk.ac.york.cs.eng2.books.dto.BookDTO;

import java.util.List;

@Repository
public interface AuthorRepository extends PageableRepository<Author, Long> {
    List<AuthorDTO> getAll();

    List<Author> getByBooksId(Long bookId);
}
