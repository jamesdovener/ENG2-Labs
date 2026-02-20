package uk.ac.york.cs.eng2.books;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import uk.ac.york.cs.eng2.books.domain.Book;

@Repository
public interface BookRepository extends PageableRepository<Book, Long>{
    boolean existsByTitleAndAuthor(String title, String author);
}
