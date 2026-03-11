package uk.ac.york.cs.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import uk.ac.york.cs.eng2.books.domain.Book;
import uk.ac.york.cs.eng2.books.domain.Publisher;

@Serdeable
@Getter @Setter
public class BookCreateDTO {
    private String title;
    private PublisherDTO publisherDTO;

    public BookCreateDTO() {

    }

    public BookCreateDTO (String title, PublisherDTO publisherDTO) {
        this.title = title;
        this.publisherDTO = publisherDTO;
    }

    public Book toBook() {
        Book book = new Book();
        book.setTitle(title);
        book.setPublisher(publisherDTO.toPublisher());

        return book;
    }

}



