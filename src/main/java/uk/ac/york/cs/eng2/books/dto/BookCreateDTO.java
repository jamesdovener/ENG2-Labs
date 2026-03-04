package uk.ac.york.cs.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import uk.ac.york.cs.eng2.books.domain.Book;

@Serdeable
@Getter @Setter
public class BookCreateDTO {
    private String title;
    private String author;

    public BookCreateDTO() {

    }

    public BookCreateDTO (String author, String title) {
        this.author = author;
        this.title = title;
    }

    public Book toBook() {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);

        return book;
    }

}



