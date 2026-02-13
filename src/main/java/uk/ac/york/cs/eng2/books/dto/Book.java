package uk.ac.york.cs.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Serdeable
@Getter @Setter
@EqualsAndHashCode
public class Book {
    private String title;
    private String author;
    private int id;
}