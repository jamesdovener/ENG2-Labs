package uk.ac.york.cs.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Serdeable
@Getter @Setter
@EqualsAndHashCode
public class BookDTO {

    private String title;
    private Long id;
    private PublisherDTO publisher;
    private Set<AuthorDTO> authors;

    public BookDTO() {}
    
}