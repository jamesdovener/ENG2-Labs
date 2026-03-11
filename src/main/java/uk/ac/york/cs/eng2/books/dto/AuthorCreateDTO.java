package uk.ac.york.cs.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Serdeable
@Setter @Getter
@EqualsAndHashCode
public class AuthorCreateDTO {
    private String name;

    public AuthorCreateDTO() {}

    public AuthorCreateDTO(String name) {
        this.name = name;
    }
}
