package uk.ac.york.cs.eng2.books.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.ac.york.cs.eng2.books.dto.BookDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@EqualsAndHashCode
public class Book {

    @Id
    @Setter @Getter
    @GeneratedValue
    private Long id;

    @Column
    @Setter @Getter
    private String title;

    @ManyToMany
    @Setter @Getter
    @EqualsAndHashCode.Exclude
    private Set<Author> authors = new HashSet<>();

    @ManyToOne
    @Setter @Getter
    @EqualsAndHashCode.Exclude
    private Publisher publisher;

    public Book() {}

    public Book(Long id, String title) {
        this.id     = id;
        this.title  = title;
    }

    public BookDTO toDTO() {
        BookDTO dto = new BookDTO();
        dto.setId(this.id);
        dto.setTitle(this.title);
        dto.setAuthors(this.authors != null
                ? this.authors.stream()
                .map(Author::toDTO)
                .collect(Collectors.toSet())
                : new HashSet<>());
        dto.setPublisher(this.publisher != null ? this.publisher.toDTO() : null);
        return dto;
    }
}
