package uk.ac.york.cs.eng2.books.domain;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Book {
    public Book() {}

    public Book(Long id, String author, String title) {
        this.id     = id;
        this.author = author;
        this.title  = title;
    }

    @Id
    @Setter @Getter
    @GeneratedValue
    private Long id;

    @Column
    @Setter @Getter
    private String author;

    @Column
    @Setter @Getter
    private String title;

}
