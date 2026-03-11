package uk.ac.york.cs.eng2.books.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.ac.york.cs.eng2.books.dto.AuthorDTO;

import java.util.Collections;
import java.util.Set;

@Entity
@EqualsAndHashCode
public class Author {

    @Id
    @GeneratedValue
    @Setter @Getter
    private Long id;

    @Column
    @Setter @Getter
    private String name;

    @ManyToMany(mappedBy = "authors")
    @Setter @Getter
    @EqualsAndHashCode.Exclude
    private Set<Book> books = Collections.emptySet();

    public Author() {}

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorDTO toDTO() {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(id);
        dto.setName(name);

        return dto;
    }
}
