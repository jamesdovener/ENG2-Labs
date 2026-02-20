package uk.ac.york.cs.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Serdeable
@Getter @Setter
@EqualsAndHashCode
public class BookDTO {

    public BookDTO() {}
    public BookDTO(Long id, String author, String title){
        this.id = id;
        this.author = author;
        this.title = title;
    }

    private String title;
    private String author;
    private Long id;
}