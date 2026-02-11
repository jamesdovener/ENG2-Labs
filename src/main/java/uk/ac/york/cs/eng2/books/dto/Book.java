package uk.ac.york.cs.eng2.books.dto;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@Serdeable
@Getter
@Setter
public class Book {
    private String title;
    private String author;
    private int id;


}
