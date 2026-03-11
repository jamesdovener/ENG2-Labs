package uk.ac.york.cs.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.ac.york.cs.eng2.books.domain.Author;

@Serdeable
@Getter @Setter
@EqualsAndHashCode
public class AuthorDTO {
    private Long id;
    private String name;

    public AuthorDTO() {}

    public AuthorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author toAuthor() {
        Author author = new Author();
        author.setId(this.id);
        author.setName(this.name);

        return author;
    }
}
