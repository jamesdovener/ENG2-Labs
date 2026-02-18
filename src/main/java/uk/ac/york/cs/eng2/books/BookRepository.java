package uk.ac.york.cs.eng2.books;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import uk.ac.york.cs.eng2.books.domain.Book;
import uk.ac.york.cs.eng2.books.dto.BookDTO;

@Repository
public interface BookRepository extends PageableRepository<Book, Long>{
}
