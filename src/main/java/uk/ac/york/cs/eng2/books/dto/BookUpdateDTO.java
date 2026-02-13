package uk.ac.york.cs.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@Serdeable
@Getter @Setter
public class BookUpdateDTO {
    private String title;
    private String author;
}
